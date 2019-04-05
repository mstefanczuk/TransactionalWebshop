package pl.mstefanczuk.transactionalwebshop.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.mstefanczuk.transactionalwebshop.cache.CategoryCache;
import pl.mstefanczuk.transactionalwebshop.exception.CategoryNotFoundException;
import pl.mstefanczuk.transactionalwebshop.model.Category;
import pl.mstefanczuk.transactionalwebshop.repository.CategoryRepository;
import pl.mstefanczuk.transactionalwebshop.service.CategoryService;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryCache categoryCache;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryCache categoryCache) {
        this.categoryRepository = categoryRepository;
        this.categoryCache = categoryCache;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Category> findAll() {
        return categoryCache.getCategories();
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        findById(category.getId());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
