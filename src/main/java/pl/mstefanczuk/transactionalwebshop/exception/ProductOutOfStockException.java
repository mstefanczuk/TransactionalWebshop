package pl.mstefanczuk.transactionalwebshop.exception;

public class ProductOutOfStockException extends RuntimeException {

    public ProductOutOfStockException(Long id) {
        super("Product " + id + " out of stock");
    }
}
