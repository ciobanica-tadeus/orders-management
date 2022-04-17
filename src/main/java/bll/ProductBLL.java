package bll;

import bll.validators.PriceValidator;
import bll.validators.StockValidator;
import bll.validators.Validator;
import connection.ConnectionFactory;
import dao.AbstractDAO;
import dao.ProductDAO;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    public ProductBLL() {
        validators = new ArrayList<Validator<Product>>();
        validators.add(new StockValidator());
        validators.add(new PriceValidator());
        productDAO = new ProductDAO();
    }

    public Product findByID(int id) {
        Product toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet resultSet = null;

        try {
            findStatement = dbConnection.prepareStatement(productDAO.createSelectQuery("idProduct"));
            findStatement.setInt(1,id);
            resultSet = findStatement.executeQuery();
            if( resultSet.next()){
                int productId = resultSet.getInt("idProduct");
                String name = resultSet.getString("name");
                double price = resultSet.getFloat("price");
                int stock = resultSet.getInt("stock");
                toReturn = new Product(productId,name,price,stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
    /**
     * Metoda pentru inserarea unui produs
     *
     * @param product
     * @return
     */
    public int insertProduct(Product product) {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        int result = productDAO.insertElement(product);
        System.out.println(result);
        if (result == -1) {
            throw new NoSuchElementException("The product can't be inserted!");
        }
        return result;
    }

    /**
     * Metoda pentru updatarea unui produs
     *
     * @param product
     * @return idproduct if update was succesfully made , otherwise return -1
     * @throws NoSuchElementException
     */
    //update DONE!!
    public int updateProduct(Product product, int id) {
        int result = productDAO.updateElement(product, id);
        System.out.println(result);
        if (result == -1) {
            throw new NoSuchElementException("The product can't be updated!");
        }

        return result;
    }

    /**
     * Method to update a product
     *
     * @param id
     * @return 0 if product was succesfully deleted or -1 if not
     * @throws NoSuchElementException
     */
    //DELETE DONE
    public int deleteProduct(int id) {
        int result = 0;
        if ((result = new ProductDAO().deleteByID(id)) == -1) {
            throw new NoSuchElementException("The product can't be deleted");
        }
        return result;
    }

    /**
     * @return list of product
     * @throws NoSuchElementException
     */
    public DefaultTableModel findAll() {
        return productDAO.findAll();
    }

    public static void main(String[] args) {
        Product firstProduct = new Product(3, "Branza", 20.2, 100);
        Product secondProduct = new Product(5, "Bacon", 8.8, 33);
        ProductBLL productBLL = new ProductBLL();
        Product result = productBLL.findByID(3);
        System.out.println(result.toString());
        //productBLL.insertProduct(firstProduct);
        //productBLL.updateProduct(secondProduct,1);
        //Product t3 = new Product(400,"Pui",7,10);
        /*DefaultTableModel products = productBLL.findAll();
        Frame frame = new Frame();
        JPanel panel = new JPanel();
        JTable table = new JTable(products);
        panel.add(table);
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(500,600);*/

    }
}
