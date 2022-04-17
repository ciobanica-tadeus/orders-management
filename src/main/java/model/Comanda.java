package model;
/**
 * Clasa Comanda reprezinta o tabela din baza de date
 * Aceasta metoda are 2 constructori,settere, gettere si metoda toString()
 */
public class Comanda {
    private int idComanda;
    private int idClient;
    private int idProduct;
    private int quantity;

    public Comanda(int id, int clientID, int productID, int quantity) {
        super();
        this.idComanda = id;
        this.idClient = clientID;
        this.idProduct = productID;
        this.quantity = quantity;
    }

    public Comanda(int clientID, int productID, int quantity) {
        super();
        this.idClient = clientID;
        this.idProduct = productID;
        this.quantity = quantity;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "idComanda=" + idComanda +
                ", idClient=" + idClient +
                ", idProduct=" + idProduct +
                ", quantity=" + quantity +
                '}';
    }
}
