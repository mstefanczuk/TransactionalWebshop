package pl.mstefanczuk.transactionalwebshop.component;

import pl.mstefanczuk.transactionalwebshop.model.Product;

public interface Cart {

    void addProductToOrder(Product product, Integer amount);

    void submitOrder();

    void cancelOrder();
}
