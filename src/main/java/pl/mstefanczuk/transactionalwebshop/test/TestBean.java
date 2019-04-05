package pl.mstefanczuk.transactionalwebshop.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import pl.mstefanczuk.transactionalwebshop.cache.CategoryCache;
import pl.mstefanczuk.transactionalwebshop.model.Category;
import pl.mstefanczuk.transactionalwebshop.model.Product;
import pl.mstefanczuk.transactionalwebshop.service.CategoryService;
import pl.mstefanczuk.transactionalwebshop.service.CartService;
import pl.mstefanczuk.transactionalwebshop.service.ProductService;

import java.io.Serializable;

@Component
@RequestScope
public class TestBean implements Serializable {

    private final CategoryService categoryService;

    private final ProductService productService;

    private final CategoryCache categoryCache;

    private CartService cartOne;
    private CartService cartTwo;

    @Autowired
    public TestBean(CategoryService categoryService, CategoryCache categoryCache, ApplicationContext applicationContext,
                    ProductService productService) {
        this.categoryService = categoryService;
        this.categoryCache = categoryCache;

        this.cartOne = applicationContext.getBean(CartService.class);
        this.cartTwo = applicationContext.getBean(CartService.class);

        this.productService = productService;
    }

    public void testFetchingAllCategories() {
        System.out.println("##### Test pobierania kategorii #####");

        System.out.println("\n# Lista kategorii przed zmianą:");
        printCategories();

        Category category = categoryService.findById(1000L);
        category.setName("Computers and software");
        categoryService.update(category);

        System.out.println("\n\n# Lista kategorii od razu po zmianie:");
        printCategories();

        System.out.println("\n\n# Odczekanie 3,5 sekund");
        wait(3500);

        System.out.println("\n# Lista kategorii po ponownym załadowaniu do cache:");
        printCategories();
    }

    public void testOneProductOrder() {
        System.out.println("\n\n##### Test koszyka: zamówienie 1 dostępnego produktu #####");

        Product product = productService.findById(2002L);
        printProductStock(product);

        addProductToCart(product, 1);
        submitOrder();

        product = productService.findById(2002L);
        printProductStock(product);
    }

    public void testOutOfStockProductOrder() {
        System.out.println("\n\n##### Test koszyka: zamówienie 1 dostepnego i 1 niedostępnego produktu #####");

        Product productOne = productService.findById(2001L);
        Product productTwo = productService.findById(2005L);
        printProductStock(productOne);
        printProductStock(productTwo);

        addProductToCart(productOne, 1);
        addProductToCart(productTwo, 1);

        submitOrder();

        productOne = productService.findById(2001L);
        productTwo = productService.findById(2005L);

        printProductStock(productOne);
        printProductStock(productTwo);
    }

    public void testTwoCartsWithTheSameProduct() {
        System.out.println("\n\n##### Test dwóch koszyków: zamówienie tych samych produktów #####");

        Product product = productService.findById(2006L);
        printProductStock(product);

        addProductToCartOne(product, 3);
        addProductToCartTwo(product, 2);

        submitOrderForCartTwo();
        printProductStock(product);

        System.out.println("# Odczekanie 2 sekund");
        wait(2000);

        submitOrderForCartOne();
        printProductStock(product);
    }

    private void printCategories() {
        categoryCache.getCategories().stream()
                .map(c -> c.getName() + " | ")
                .forEach(System.out::print);
    }

    private void wait(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printProductStock(Product product) {
        System.out.println("# Liczba " + product.getName() + " w magazynie: " + product.getStock());
    }

    private void addProductToCart(Product product, Integer amount) {
        System.out.println("# Dodanie " + amount + " " + product.getName() + " do koszyka");
        cartOne.addProductToOrder(product, amount);
    }

    private void addProductToCartOne(Product product, Integer amount) {
        System.out.println("# Dodanie " + amount + " " + product.getName() + " do pierwszego koszyka");
        cartOne.addProductToOrder(product, amount);
    }

    private void addProductToCartTwo(Product product, Integer amount) {
        System.out.println("# Dodanie " + amount + " " + product.getName() + " do drugiego koszyka");
        cartTwo.addProductToOrder(product, amount);
    }

    private void submitOrder() {
        System.out.println("# Złożenie zamówienia");
        cartOne.submitOrder();
    }

    private void submitOrderForCartOne() {
        System.out.println("# Złożenie zamówienia pierwszego koszyka");
        cartOne.submitOrder();
    }

    private void submitOrderForCartTwo() {
        System.out.println("# Złożenie zamówienia drugiego koszyka");
        cartTwo.submitOrder();
    }
}
