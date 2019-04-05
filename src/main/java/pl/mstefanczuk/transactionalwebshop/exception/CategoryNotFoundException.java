package pl.mstefanczuk.transactionalwebshop.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long id) {
        super("Could not find category " + id);
    }
}
