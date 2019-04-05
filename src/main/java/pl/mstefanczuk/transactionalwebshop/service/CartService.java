package pl.mstefanczuk.transactionalwebshop.service;

import pl.mstefanczuk.transactionalwebshop.model.Product;

public interface CartService {

    void addProductToOrder(Product product, Integer amount);

    void submitOrder();

    void cancelOrder();
}
