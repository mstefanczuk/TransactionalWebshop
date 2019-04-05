package pl.mstefanczuk.transactionalwebshop.service;

import pl.mstefanczuk.transactionalwebshop.model.Category;

import java.util.List;

public interface CategoryService {

    Category findById(Long id);

    List<Category> findAll();

    Category create(Category category);

    Category update(Category category);

    void deleteById(Long id);
}
