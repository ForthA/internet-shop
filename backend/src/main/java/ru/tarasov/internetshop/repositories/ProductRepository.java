package ru.tarasov.internetshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tarasov.internetshop.models.Product;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Integer> {
    Product findProductById(int id);

    @Query(value = """
    select * from product p
    where p.category_id in (with RECURSIVE query as (
        select id, father_id, title from category
        where id = ?1
        UNION all
    
        select c.id, c.father_id, c.title from category as c
                                                   inner join query on query.id = c.father_id
    )
    
                            select id from query)
""", nativeQuery = true)
    List<Product> findProductsByCategoryId(int id);
}
