package bll;

import connection.ConnectionFactory;
import dao.ComandaDAO;
import model.Comanda;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class OrderBLL {
    private ComandaDAO comandaDAO;

    public OrderBLL(){
        comandaDAO = new ComandaDAO();

    }

    /**
     * Metoda cauta o comanda dupa un ID primit ca si parametru.
     * @param id
     * @return Comanda
     */
    public Comanda findComandaById(int id) {
            Comanda toReturn = null;

            Connection dbConnection = ConnectionFactory.getConnection();
            PreparedStatement findStatement = null;
            ResultSet resultSet = null;

            try {
                findStatement = dbConnection.prepareStatement(comandaDAO.createSelectQuery("idComanda"));
                findStatement.setInt(1,id);
                resultSet = findStatement.executeQuery();
                if( resultSet.next()){
                    int idComanda = resultSet.getInt("idComanda");
                    int idClient = resultSet.getInt("idClient");
                    int idProdus = resultSet.getInt("idProduct");
                    int quantity = resultSet.getInt("quantity");
                    toReturn = new Comanda(idComanda,idClient,idProdus,quantity);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return toReturn;
    }

    /**
     * Metoda insereaza o comanda
     *
     * @param comanda
     * @return id-ul comenzii daca a fost inserat cu succes , altfel -1;
     * @throws NoSuchElementException
     */
    public int insertComanda(Comanda comanda) {
        int result = comandaDAO.insertElement(comanda);
        if (result == -1) {
            throw new NoSuchElementException("The product can't be inserted!");
        }
        return result;
    }

    /**
     * Metoda pentru updatarea unei comenzi dupa ID
     *
     * @param comanda, id
     * @return id-ul comenzii daca a fost editata cu succes, altfel -1;
     * @throws NoSuchElementException
     */
    public int updateComanda(Comanda comanda, int id) {
        int result = comandaDAO.updateElement(comanda, id);
        if (result == -1) {
            throw new NoSuchElementException("The product can't be updated!");
        }

        return result;
    }

    /**
     * Metoda pentru stergerea unei comenzi dupa ID
     *
     * @param id
     * @return 0 daca a fost stearsa comanda cu succes , altfel -1;
     * @throws NoSuchElementException
     */
    public int deleteComanda(int id) {
        int result = 0;
        if ((result = comandaDAO.deleteByID(id)) == -1) {
            throw new NoSuchElementException("The product can't be deleted");
        }
        return result;
    }

    /**
     * @return defaultTableModel of order
     * @throws NoSuchElementException
     */
    public DefaultTableModel findAll() {
        return comandaDAO.findAll();
    }

    public static void main(String[] args) {
        OrderBLL orderBLL = new OrderBLL();
        orderBLL.findComandaById(4);
    }
}
