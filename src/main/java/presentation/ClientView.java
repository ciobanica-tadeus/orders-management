package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientView extends JFrame {
    private JLabel idLabel = new JLabel("ID:");
    private JTextField idText = new JTextField();
    private JLabel nameLabel = new JLabel("Name:");
    private JTextField nameText = new JTextField();
    private JLabel addressLabel = new JLabel("Address:");
    private JTextField addressText = new JTextField();
    private JLabel emailLabel = new JLabel("Email:");
    private JTextField emailText = new JTextField();
    private JLabel ageLabel = new JLabel("Age");
    private JTextField ageText = new JTextField();
    private JButton addBtn = new JButton("Add client");
    private JButton editBtn = new JButton("Edit client");
    private JButton deleteBtn = new JButton("Delete client");
    private JButton allBtn = new JButton("View clients");
    private JPanel panel = new JPanel();

    public ClientView() {
        idLabel.setBounds(10, 10, 100, 20);
        idText.setBounds(110, 10, 200, 20);
        nameLabel.setBounds(10, 40, 100, 20);
        nameText.setBounds(110, 40, 200, 20);
        addressLabel.setBounds(10, 70, 100, 20);
        addressText.setBounds(110, 70, 200, 20);
        emailLabel.setBounds(10, 100, 200, 20);
        emailText.setBounds(110, 100, 200, 20);
        ageLabel.setBounds(10, 130, 200, 20);
        ageText.setBounds(110, 130, 200, 20);
        addBtn.setBounds(10, 170, 120, 30);
        editBtn.setBounds(140, 170, 120, 30);
        deleteBtn.setBounds(270, 170, 120, 30);
        allBtn.setBounds(120, 210, 150, 30);

        panel.add(idLabel);
        panel.add(idText);
        panel.add(nameLabel);
        panel.add(nameText);
        panel.add(addressLabel);
        panel.add(addressText);
        panel.add(emailLabel);
        panel.add(emailText);
        panel.add(ageLabel);
        panel.add(ageText);
        panel.add(addBtn);
        panel.add(editBtn);
        panel.add(deleteBtn);
        panel.add(allBtn);
        panel.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Client Operations");
        this.setContentPane(panel);
        this.setSize(420, 300);
    }

    public int getIdText() {
        return Integer.parseInt(idText.getText());
    }

    public String getNameText() {
        return nameText.getText();
    }

    public String getAddressText() {
        return addressText.getText();
    }

    public String getEmailText() {
        return emailText.getText();
    }

    public int getAgeText() {
        return Integer.parseInt(ageText.getText());
    }

    public void insertClientListener(ActionListener actionListener) {
        addBtn.addActionListener(actionListener);
    }

    public void editClientListener(ActionListener actionListener) {
        editBtn.addActionListener(actionListener);
    }

    public void deleteClientListener(ActionListener actionListener) {
        deleteBtn.addActionListener(actionListener);
    }

    public void viewAllClientsListener(ActionListener actionListener) {
        allBtn.addActionListener(actionListener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(panel, message);
    }

    public static void main(String[] args) {
        ClientView clientView = new ClientView();
        clientView.setVisible(true);
    }
}
