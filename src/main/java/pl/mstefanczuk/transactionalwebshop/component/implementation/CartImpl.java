package pl.mstefanczuk.transactionalwebshop.component.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import pl.mstefanczuk.transactionalwebshop.component.Cart;
import pl.mstefanczuk.transactionalwebshop.dto.OrderItem;
import pl.mstefanczuk.transactionalwebshop.model.Product;
import pl.mstefanczuk.transactionalwebshop.service.ProductService;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CartImpl implements Cart {

    private Map<Long, OrderItem> orderItems;

    private final ProductService productService;

    @Autowired
    public CartImpl(ProductService productService) {
        this.productService = productService;
        this.orderItems = new HashMap<>();
    }

    @Override
    public void addProductToOrder(Product product, Integer amount) {
        Product productFromDB = productService.findById(product.getId());
        if (productFromDB.getStock() <= 0) {
            System.out.println("Produkt " + product.getName() + " nie może zostać dodany do koszyka," +
                    " ponieważ nie ma go w magazynie.");
            return;
        }

        OrderItem orderItem;

        if (orderItems.containsKey(product.getId())) {
            orderItem = orderItems.get(product.getId());
            orderItem.setAmount(orderItem.getAmount() + amount);
        } else {
            orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setAmount(amount);
        }

        orderItems.put(product.getId(), orderItem);
    }

    @Override
    @Transactional
    public void submitOrder() {
        orderItems.forEach((id, item) -> updateProductStock(item));
        orderItems = new HashMap<>();
    }

    @Override
    public void cancelOrder() {
        orderItems = new HashMap<>();
    }

    private void updateProductStock(OrderItem orderItem) {
        Product product = productService.findById(orderItem.getProduct().getId());
        product.setStock(product.getStock() - orderItem.getAmount());

        if(product.getStock() < 0) {
            //przerwanie transakcji wyjatkiem
            //throw new ProductOutOfStockException(product.getId());

            //przerwanie transakcji bez wyjatku
            System.out.println("Zbyt mała ilość produktu " + product.getName() + " na stanie. Transakcja wycofana.");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } else {
            productService.update(product);
        }
    }
}
