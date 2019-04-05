package pl.mstefanczuk.transactionalwebshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mstefanczuk.transactionalwebshop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
