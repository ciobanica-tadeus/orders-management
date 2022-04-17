package presentation;

import bll.ClientBLL;
import bll.ProductBLL;
import model.Client;
import model.Product;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clasa Controller este folosita pentru a gestiona actiunile pentru fiecare button a aplicatiei.
 */
public class Controller {
    private View view;
    private ProdusView produsView;
    private ClientView clientView;
    private OrderView orderView;

    public Controller(View view) {
        this.view = view;
        view.setVisible(true);
        view.addClientListener(new ClientListener());
        view.addProductListener(new ProductListener());
        view.addOrderListener(new OrderListener());
    }

    class ClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clientView = new ClientView();
            clientView.setVisible(true);
            clientView.insertClientListener(new InsertClientListener());
            clientView.editClientListener(new UpdateClientListener());
            clientView.deleteClientListener(new DeleteClientListener());
            clientView.viewAllClientsListener(new ViewAllClientListener());
        }
    }

    class ProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            produsView = new ProdusView();
            produsView.setVisible(true);
            produsView.insertProductListener(new InsertProductListener());
            produsView.updateClientListener(new UpdateProductListener());
            produsView.deleteProductListener(new DeleteProductListener());
            produsView.viewAllProductsListener(new ViewAllProductListener());
        }
    }

    class OrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProductBLL productBLL = new ProductBLL();
            ClientBLL clientBLL = new ClientBLL();
            orderView = new OrderView(productBLL.findAll(), clientBLL.findAll());
            orderView.setVisible(true);
        }
    }

    class InsertProductListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String nameProduct = produsView.getNameText();
            double price = produsView.getPriceText();
            int stock = produsView.getStockText();
            if (produsView.getNameText().equals("")) {
                produsView.showMessage("Introduceti un nume valid!");
            } else if (produsView.getPriceText() < 0) {
                produsView.showMessage("Introduceti un pret valid!");
            } else if (produsView.getStockText() < 0) {
                produsView.showMessage("Introduceti o cantitatea valida pentru stock!");
            } else {
                ProductBLL productBLL = new ProductBLL();
                Product product = new Product(nameProduct, price, stock);
                try {
                    int newId = productBLL.insertProduct(product);
                    if (newId != -1) {
                        produsView.showMessage("Inserare produs cu succes!");
                    }
                    product.setIdProduct(newId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    class UpdateProductListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = produsView.getIdText();
            String nameProduct = produsView.getNameText();
            double price = produsView.getPriceText();
            int stock = produsView.getStockText();
            if (produsView.getNameText().equals("")) {
                produsView.showMessage("Introduceti un nume valid!");
            } else if (produsView.getPriceText() < 0) {
                produsView.showMessage("Introduceti un pret valid!");
            } else if (produsView.getStockText() < 0) {
                produsView.showMessage("Introduceti o cantitatea valida pentru stock!");
            } else {
                ProductBLL productBLL = new ProductBLL();
                Product product = new Product(id, nameProduct, price, stock);
                try {
                    int newId = productBLL.updateProduct(product, id);
                    if (newId != -1) {
                        produsView.showMessage("Editarea produs cu id = " + id + " cu succes!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    class DeleteProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = produsView.getIdText();
            ProductBLL productBLL = new ProductBLL();
            try {
                int newId = productBLL.deleteProduct(id);
                if (newId != -1) {
                    produsView.showMessage("Stergere produs cu id = " + id + " cu succes!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class ViewAllProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProductBLL productBLL = new ProductBLL();
            try {
                TableView tableview = new TableView(productBLL.findAll());
                produsView.setVisible(false);
                tableview.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class InsertClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String nameClient = clientView.getNameText();
            String address = clientView.getAddressText();
            String email = clientView.getEmailText();
            int age = clientView.getAgeText();
            if (clientView.getEmailText().contains("@")) {
                if(clientView.getAgeText() > 0){
                    ClientBLL clientBLL = new ClientBLL();
                    Client client = new Client(nameClient, address, email, age);
                    try {
                        int newId = clientBLL.insertClient(client);
                        if (newId != -1) {
                            clientView.showMessage("Adaugare client cu succes!");
                        }
                        client.setIdClient(newId);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }else{
                 clientView.showMessage("Introduceti o varsta valida!");
                }
            } else {
                clientView.showMessage("Introduceti un email valid!");
            }

        }
    }

    class UpdateClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = clientView.getIdText();
            String nameClient = clientView.getNameText();
            String address = clientView.getAddressText();
            String email = clientView.getEmailText();
            int age = clientView.getAgeText();
            if (clientView.getEmailText().contains("@")) {
                if(clientView.getAgeText() > 0){
                    ClientBLL clientBLL = new ClientBLL();
                    Client client = new Client(nameClient, address, email, age);
                    try {
                        int newId = clientBLL.updateClient(client, id);
                        if (newId != -1) {
                            clientView.showMessage("Editarea clientului cu id = " + id + " a avut succes!");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }else{
                    clientView.showMessage("Introduceti o varsta valida!");
                }

            } else {
                clientView.showMessage("Introduceti un email valid!");
            }


        }
    }

    class DeleteClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = clientView.getIdText();
            ClientBLL clientBLL = new ClientBLL();
            try {
                int newId = clientBLL.deleteClient(id);
                if (newId != -1) {
                    clientView.showMessage("Stergerea clientului cu id = " + id + " a avut succes!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class ViewAllClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ClientBLL clientBLL = new ClientBLL();
            try {
                TableView tableview = new TableView(clientBLL.findAll());
                clientView.setVisible(false);
                tableview.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
