package pl.mstefanczuk.transactionalwebshop.dto;

import lombok.Data;
import pl.mstefanczuk.transactionalwebshop.model.Product;

import java.io.Serializable;

@Data
public class OrderItem implements Serializable {
    private Product product;
    private Integer amount;
}
