package pl.mstefanczuk.transactionalwebshop.cache;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.mstefanczuk.transactionalwebshop.model.Category;
import pl.mstefanczuk.transactionalwebshop.repository.CategoryRepository;

import java.util.List;

@Component
public class CategoryCache {

    @Getter
    private List<Category> categories;

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryCache(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Scheduled(fixedRate = 3000)
    private void refreshCategories() {
        categories = categoryRepository.findAll();
    }
}
