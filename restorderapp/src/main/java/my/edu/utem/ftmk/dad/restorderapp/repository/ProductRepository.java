package my.edu.utem.ftmk.dad.restorderapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.edu.utem.ftmk.dad.restorderapp.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
