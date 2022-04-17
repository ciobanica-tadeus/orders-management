package presentation;

import bll.ProductBLL;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Clasa TableView este interfata in care se va afisa rezultatul operatiei de SELECT ALL
 *
 */
public class TableView extends JFrame {
    private JPanel panel = new JPanel();
    private JTable table;

    public TableView(DefaultTableModel defaultTableModel) {
        table = new JTable(defaultTableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        //panel.add(scrollPane);
        this.setContentPane(panel);
        this.pack();
        this.setTitle("View all");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 400);
    }

    public static void main(String[] args) {
        ProductBLL productBLL = new ProductBLL();
        TableView tableView = new TableView(productBLL.findAll());
        tableView.setVisible(true);
    }
}