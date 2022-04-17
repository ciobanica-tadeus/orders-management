package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

import javax.swing.table.DefaultTableModel;

/**
 * Clasa AbstractDAO este clasa in care se fac principalele operatii pe baza de date (INSERT, SELECT, UPDATE, DELETE)
 * Ea implementeaza metode generice pentru fiecare metoda de lucru cu BD
 * @param <T>
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Creaza String-ul de SELECT pentru BD
     * @param field
     * @return
     */
    public String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =? ");
        return sb.toString();
    }

    /**
     * Creeaza String-ul de INSERT pentru BD
     * @return
     */
    private String createInsertQuery() {
        StringBuilder insert = new StringBuilder();
        StringBuilder values = new StringBuilder();
        insert.append("INSERT INTO " + type.getSimpleName() + "(");
        values.append("VALUES (");

        for (Field field : type.getDeclaredFields()) {
            if (!field.equals(type.getDeclaredFields()[0])) {
                insert.append(field.getName() + ",");
                values.append("?,");
            }
        }
        insert.delete(insert.length() - 1, insert.length());
        insert.append(")");
        values.delete(values.length() - 1, values.length());
        values.append(")");
        //System.out.println(insert + values.toString());
        return insert + values.toString();
    }

    /**
     * Creeaza String-ul de UPDATE pentru BD
     * @return
     */
    private String createUpdateQuery() {
        StringBuilder update = new StringBuilder();
        update.append("UPDATE " + type.getSimpleName() + " SET ");
        for (Field field : type.getDeclaredFields()) {
            if (!field.equals(type.getDeclaredFields()[0])) {
                update.append(field.getName());
                update.append("=?,");
            }
        }
        update.delete(update.length() - 1, update.length());
        update.append(" WHERE ").append(type.getDeclaredFields()[0].getName()).append(" =  ? ");
        //System.out.println("Update query");
        //System.out.println(update.toString());
        return update.toString();
    }

    /**
     * Creeaza String-ul de DELETE pentru BD
     * @return
     */
    private String createDeleteQuery(String field) {
        //DELETE FROM table_name WHERE condition;
        StringBuilder delete = new StringBuilder();
        delete.append("DELETE FROM ");
        delete.append(type.getSimpleName());
        delete.append(" WHERE " + field + " = ?");
        //System.out.println(delete.toString());
        return delete.toString();
    }

    /**
     * Creeaza un defaultTableModel pentru a afisa toate datele din BD intr-un JTable
     * @param rs
     * @return
     * @throws SQLException
     */
    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

    public DefaultTableModel findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();

        try {
            connection = ConnectionFactory.getConnection();
            statement = ConnectionFactory.getConnection().prepareStatement(query);
            resultSet = statement.executeQuery();

            return buildTableModel(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(type.getDeclaredFields()[0].getName());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<T> list = createObjects(resultSet);
            for (T t : list) {
                return t;
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Metoda care efectueaza operatia de INSERT in BD
     * @param t
     * @return id-ul produsul sau -1 daca nu a fost inserat cu succes
     */
    public int insertElement(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createInsertQuery();
        int insertedID = 1;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (Field field : type.getDeclaredFields()) {
                if (!field.equals(type.getDeclaredFields()[0])) {
                    field.setAccessible(true);
                    Object obj = field.get(t);
                    statement.setString(insertedID, obj.toString());
                    insertedID++;
                }
            }
            //System.out.println(statement.toString());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                insertedID = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return insertedID;
    }

    /**
     * Metoda care efectueaza operatia de UPDATE dupa ID
     * @param t
     * @param id
     * @return id-ul produsului updatat sau -1 in caz de eroare
     */
    public int updateElement(T t, int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createUpdateQuery();
        int updateID = 1;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (Field field : type.getDeclaredFields()) {
                if (!field.equals(type.getDeclaredFields()[0])) {
                    field.setAccessible(true);
                    Object obj = field.get(t);
                    statement.setString(updateID, obj.toString());
                    updateID++;
                }
            }
            statement.setInt(updateID, id);
            //System.out.println(statement.toString());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                updateID = resultSet.getInt(1);
            }
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return -1;
    }

    /**
     * Metoda care efectueaza operatia de DELETE dupa ID
     * @param id
     * @return 1 in caz de succes , -1 in caz contrar
     */
    public int deleteByID(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteQuery("id" + type.getSimpleName());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            //System.out.println(statement.toString());
            statement.executeUpdate();
            statement.getGeneratedKeys();
            return 1;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return -1;
    }
}
