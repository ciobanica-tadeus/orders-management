package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Comanda;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * Clasa OrderView care contine 2 tabele in care se vizualizeaza produsele si clientii
 * Se introduce cantitatea pentru produs si se poate plasa o comanda astfel
 * Contine un constructor in care se initializeaza toata interfata cu componente
 */
public class OrderView extends JFrame implements ActionListener {
    private JPanel panel = new JPanel();
    private JTable tableProduct;
    private JTable tableClient;

    private JTextField textField;
    private DefaultTableModel productModel;
    private DefaultTableModel clientModel;
    private JPanel mainPanel = new JPanel();
    private JPanel clientPanel = new JPanel();
    private JPanel productPanel = new JPanel();
    private JButton button;

    public OrderView(DefaultTableModel productModel, DefaultTableModel clientModel) {
        this.productModel = productModel;
        this.clientModel = clientModel;
        mainPanel = new JPanel();
        clientPanel = new JPanel();
        productPanel = new JPanel();
        tableProduct = new JTable(productModel);
        JScrollPane scrollPaneProduct = new JScrollPane(tableProduct);
        tableClient = new JTable(clientModel);
        JScrollPane scrollPaneClient = new JScrollPane(tableClient);
        productPanel.add(scrollPaneProduct);
        clientPanel.add(scrollPaneClient);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(productPanel, BorderLayout.WEST);
        mainPanel.add(clientPanel, BorderLayout.EAST);

        JPanel downPanel = new JPanel();
        JLabel label = new JLabel("Selectați clientul și produsul din tabelă.Apoi introduceti cantitatea dorita");
        label.setFont(new Font("Times New Roman", Font.BOLD, 20));
        label.setSize(300, 20);
        textField = new JTextField();
        button = new JButton("Add order");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        downPanel.add(label);
        downPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        downPanel.add(textField);
        downPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        downPanel.add(button);
        downPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        BoxLayout boxLayout = new BoxLayout(downPanel, BoxLayout.Y_AXIS);
        downPanel.setLayout(boxLayout);
        button.addActionListener(this);
        mainPanel.add(downPanel, BorderLayout.SOUTH);
        this.add(mainPanel);
        this.pack();
        this.setSize(1000, 600);
        this.setTitle("Order Management");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }


    public static void main(String[] args) {
        OrderView orderView = new OrderView(new ProductBLL().findAll(), new ClientBLL().findAll());
        orderView.setVisible(true);
        //System.out.println(orderView.tableClient.);

    }

    /**
     * Metoda care obtine id-ul produsului selectat in JTable
     * @return String
     */
    public String obtainIdProduct (){
        return productModel.getValueAt(tableProduct.getSelectedRow(),0).toString();
    }

    /**
     * Metoda care obtine id-ul clientului selectat in JTable
     * @return String
     */
    public String obtainIdClient(){
        return clientModel.getValueAt(tableClient.getSelectedRow(),0).toString();
    }

    /**
     * Metoda care obtine cantitatea comenzii scrisa in textField la plasarea acestei comenzi
     * @return String
     */
    public String obtainStockData(){
        return productModel.getValueAt(tableProduct.getSelectedRow(),3).toString();
    }

    /**
     * Metoda care implementeaza interfata ActionListener
     * Aceasta metoda plaseaza verifica datele introduse si plaseaza o comanda. Cantitatea produsului comandat este decrementata.
     * Dupa plasarea comenzii se scrie intr-un fisier .txt sumarul comenzii.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            if (tableClient.getSelectedRow() == -1 || tableProduct.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(button, "Selecati un produs si un client!");
            } else if (textField.getText().equals("")) {
                JOptionPane.showMessageDialog(button, "Introduceti o cantitate valida!");
            } else {
                int productID = Integer.parseInt(obtainIdProduct());
                //System.out.println("Id-ul produsului selectat este : " + productID);
                int clientID = Integer.parseInt(obtainIdClient());
                //System.out.println("Id-ul clientului selectat este : " + clientID);
                int availableStock = Integer.parseInt(obtainStockData());
                //System.out.println("Cantitatea aleasa este : " + availableStock);
                int insertStock = Integer.parseInt(textField.getText());
                if( availableStock < insertStock ){
                    JOptionPane.showMessageDialog(button,"Stoc insuficient pentru aceasta comanda!");
                }else{
                    ProductBLL productBLL = new ProductBLL();
                    ClientBLL clientBLL = new ClientBLL();
                    OrderBLL orderBLL = new OrderBLL();
                    Comanda comanda = new Comanda(clientID,productID,insertStock);
                    int comandaID = orderBLL.insertComanda(comanda);
                    comanda.setIdComanda(comandaID);
                    JOptionPane.showMessageDialog(button,"Comanda adaugata cu succes!");
                    Client client = clientBLL.findClientById(clientID);
                    Product product = productBLL.findByID( productID);
                    product.setStock(product.getStock() - insertStock);
                    productBLL.updateProduct(product,productID);
                    try {
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        PrintWriter printWriter = new PrintWriter("E:\\TP\\PT2022_30221_Ciobanica_Tadeus_Assignment_3\\Comanda_" + comandaID + "_bill.txt");
                        printWriter.write("ID Comanda: " + comandaID + "\n\n");
                        printWriter.write("Client: " + client.getName() + "\n");
                        printWriter.write("Address: " + client.getAddress() + "\n");
                        printWriter.write("Email: " + client.getEmail() + "\n");
                        printWriter.write("Age: " + client.getAge() + "\n\n");
                        printWriter.write("Produsul: " +product.getName() + "\n");
                        printWriter.write("Pret: " + String.format("%.2f",product.getPrice()) + " lei\n");
                        printWriter.write("Cantitate: " + insertStock +"\n");
                        printWriter.write("Pret total: " + String.format("%.2f",product.getPrice() * insertStock) + " lei\n");
                        printWriter.close();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }


                }
            }
        }
    }
}
