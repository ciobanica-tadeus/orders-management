package bll;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import connection.ConnectionFactory;
import dao.ClientDAO;
import model.Client;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
        clientDAO = new ClientDAO();
    }

    /**
     * Metoda cauta un client dupa un ID primit ca si parametru.
     * @param id
     * @return Client
     */
    public Client findClientById(int id) {
        Client toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet resultSet = null;

        try {
            findStatement = dbConnection.prepareStatement(clientDAO.createSelectQuery("idClient"));
            findStatement.setInt(1,id);
            resultSet = findStatement.executeQuery();
            if( resultSet.next()){
                int idClient = resultSet.getInt("idClient");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                toReturn = new Client(idClient,name,address,email,age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    /**
     * Metoda pentru inserarea unui client.
     * Verifica pentru inceput daca este un client valid dupa care face insert
     * @return int
     * @throws NoSuchElementException
     */
    public int insertClient(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        int result = clientDAO.insertElement(client);
        if (result == -1) {
            throw new NoSuchElementException("The product can't be inserted!");
        }
        return result;
    }

    /**
     * Metoda pentru updatarea unui client
     * @return idproduct daca produsul a fost inserat cu succes , altfel -1
     * @throws NoSuchElementException
     */
    public int updateClient(Client client, int id) {
        int result = clientDAO.updateElement(client, id);
        System.out.println(result);
        if (result == -1) {
            throw new NoSuchElementException("The product can't be updated!");
        }

        return result;
    }

    /**
     * Metoda pentru stergerea unui produs dupa ID
     * @param id
     * @return 0 daca stergerea a avut loc cu succes si -1 altfel
     * @throws NoSuchElementException
     */
    public int deleteClient(int id) {
        int result = 0;
        if ((result = clientDAO.deleteByID(id)) == -1) {
            throw new NoSuchElementException("The product can't be deleted");
        }
        return result;
    }

    /** Metoda care returneaza un DefaultTableModel cu toti clientii din baza de date
     * @return defaultTableModel
     * @throws NoSuchElementException
     */
    public DefaultTableModel findAll() {
        return clientDAO.findAll();
    }

    public static void main(String[] args) {
        Client client1 = new Client("George Vadim", "Str.Arborelui", "geroge@gmail.com", 20);
        Client c2 = new Client("Diana", "STR.BUFTEA", "diana20@yahoo.com", 19);
        ClientBLL clientBLL = new ClientBLL();
        Client client = clientBLL.findClientById(1);
        System.out.println(client.toString());
    }
}
