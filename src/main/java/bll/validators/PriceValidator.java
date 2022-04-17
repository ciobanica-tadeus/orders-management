package bll.validators;

import model.Product;
/**
 * Clasa PriceValidator implementeaza interfata Validator
 * Metoda validate() primeste un produs ca si parametru si verifica daca pretul produsului este corect.
 * Pretul unui produs nu poate fi negativ
 */
public class PriceValidator implements Validator<Product> {


    @Override
    public void validate(Product product) {
        if (product.getPrice() < 0){
            throw new IllegalArgumentException("The product price is negative");
        }
    }
}
