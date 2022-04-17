package model;

/**
 * Clasa Product reprezinta o tabela din baza de date
 * Aceasta metoda are 2 constructori,settere ,gettere si metoda toString()
 */
public class Product {
    private int idProduct;
    private String name;
    private double price;
    private int stock;

    public Product(int idProduct, String name, double price, int stock) {
        super();
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Product(String name, double price, int stock) {
        super();
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
