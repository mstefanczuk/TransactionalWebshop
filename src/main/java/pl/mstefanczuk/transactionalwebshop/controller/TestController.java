package pl.mstefanczuk.transactionalwebshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mstefanczuk.transactionalwebshop.test.TestBean;

@RestController
public class TestController {

    private final TestBean testBean;

    @Autowired
    public TestController(TestBean testBean) {
        this.testBean = testBean;
    }

    @GetMapping("test-categories")
    public void testCategories() {
        testBean.testFetchingAllCategories();
    }

    @GetMapping("test-cart-one-product")
    public void testCartOneProduct() {
        testBean.testOneProductOrder();
    }

    @GetMapping("test-cart-product-out-of-stock")
    public void testCartOneProductOutOfStock() {
        testBean.testOutOfStockProductOrder();
    }

    @GetMapping("test-two-carts-same-products")
    public void testTwoCartsSameProducts() {
        testBean.testTwoCartsWithTheSameProduct();
    }
}
