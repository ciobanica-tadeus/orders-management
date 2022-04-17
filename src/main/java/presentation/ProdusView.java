package presentation;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Clasa ProdusView afiseaza interfata pentru operatiile pe produse
 * Aceasta initializeaza componentele in constructor. Are gettere pentru fiecare textField
 * Metode care adauga actiuni fiecaruia dintre butoanele acesteia, precum si o metoda de afisare a unui mesaj in caz de erori sau date introduse gresit
 */
public class ProdusView extends JFrame {
    private JLabel idLabel = new JLabel("ID:");
    private JTextField idText = new JTextField();
    private JLabel nameLabel = new JLabel("Name:");
    private JTextField nameText = new JTextField();
    private JLabel priceLabel = new JLabel("Price:");
    private JTextField priceText = new JTextField();
    private JLabel stockLabel = new JLabel("Stock:");
    private JTextField stockText = new JTextField();
    private JButton addBtn = new JButton("Add product");
    private JButton editBtn = new JButton("Edit product");
    private JButton deleteBtn = new JButton("Delete product");
    private JButton allBtn = new JButton("View products");
    private JPanel panel = new JPanel();

    public ProdusView() {
        idLabel.setBounds(10, 10, 100, 20);
        idText.setBounds(110, 10, 200, 20);
        nameLabel.setBounds(10, 40, 100, 20);
        nameText.setBounds(110, 40, 200, 20);
        priceLabel.setBounds(10, 70, 100, 20);
        priceText.setBounds(110, 70, 200, 20);
        stockLabel.setBounds(10, 100, 200, 20);
        stockText.setBounds(110, 100, 200, 20);
        addBtn.setBounds(10, 140, 120, 30);
        editBtn.setBounds(140, 140, 120, 30);
        deleteBtn.setBounds(270, 140, 120, 30);
        allBtn.setBounds(120, 180, 150, 30);

        panel.add(idLabel);
        panel.add(idText);
        panel.add(nameLabel);
        panel.add(nameText);
        panel.add(priceLabel);
        panel.add(priceText);
        panel.add(stockLabel);
        panel.add(stockText);
        panel.add(addBtn);
        panel.add(editBtn);
        panel.add(deleteBtn);
        panel.add(allBtn);
        panel.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Product Operations");
        this.setContentPane(panel);
        this.setSize(420, 280);
    }

    public int getIdText() {
        return Integer.parseInt(idText.getText());
    }

    public String getNameText() {
        return nameText.getText();
    }

    public double getPriceText() {
        return Double.parseDouble(priceText.getText());
    }

    public int getStockText() {
        return Integer.parseInt(stockText.getText());
    }

    public void insertProductListener(ActionListener actionListener) {
        addBtn.addActionListener(actionListener);
    }

    public void updateClientListener(ActionListener actionListener) {
        editBtn.addActionListener(actionListener);
    }

    public void deleteProductListener(ActionListener actionListener) {
        deleteBtn.addActionListener(actionListener);
    }

    public void viewAllProductsListener(ActionListener actionListener) {
        allBtn.addActionListener(actionListener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(panel, message);
    }

    public static void main(String[] args) {
        ProdusView produsView = new ProdusView();
        produsView.setVisible(true);
    }
}
