package ru.tarasov.internetshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tarasov.internetshop.models.Category;
import ru.tarasov.internetshop.models.Product;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Integer> {
    Product findProductById(int id);

    @Query(value = """
    select * from product
    where category_id = ?1
""", nativeQuery = true)
    List<Product> findProductsByCategoryId(int id);
}
