package pl.mstefanczuk.transactionalwebshop.service;

import pl.mstefanczuk.transactionalwebshop.model.Product;

import java.util.List;

public interface ProductService {

    Product findById(Long id);

    List<Product> findAll();

    Product create(Product product);

    Product update(Product product);

    void deleteById(Long id);
}
