package presentation;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Clasa View reprezinta interfata principala care ne ofera 3 actiuni: - administrarea produselor
 *                                                                     - administrarea clientilor
 *                                                                     - plasareaa unei comenzi
 * Aceasta initializeaza toate componentele si adauga actiuni pentru fiecare dintre butoanele interfetei
 */
public class View extends JFrame {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel("Order Management");
    private JButton product = new JButton("Manage product");
    private JButton client = new JButton("Manage client");
    private JButton order = new JButton("Manage order");

    public View() {
        //label.setBounds(100,10,250,50);
        client.setBounds(50, 50, 250, 30);
        product.setBounds(50, 90, 250, 30);
        order.setBounds(50, 130, 250, 30);
        panel.add(product);
        panel.add(client);
        panel.add(order);
        //panel.add(label);
        panel.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Order Management");
        this.setContentPane(panel);
        this.setSize(400, 250);
    }

    public void addClientListener(ActionListener actionListener) {
        client.addActionListener(actionListener);
    }

    public void addProductListener(ActionListener actionListener) {
        product.addActionListener(actionListener);
    }

    public void addOrderListener(ActionListener actionListener) {
        order.addActionListener(actionListener);
    }

    public static void main(String[] args) {
        View view = new View();
        view.setVisible(true);
    }
}
