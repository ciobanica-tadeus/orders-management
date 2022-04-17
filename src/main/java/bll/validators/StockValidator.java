package bll.validators;

import model.Product;

/**
 * Clasa StockValidator implementeaza interfata Validator
 * Metoda validate() primeste un produs ca si parametru si verifica daca stock-ul produsului este corect.
 * Stock-ul unui produs nu poate fi negativ
 */

public class StockValidator implements Validator<Product>{
    @Override
    public void validate(Product product) {
        if( product.getStock() < 0){
            throw new IllegalArgumentException("The product stock is not respected");
        }
    }
}
