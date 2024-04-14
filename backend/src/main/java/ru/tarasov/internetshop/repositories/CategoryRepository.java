package ru.tarasov.internetshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tarasov.internetshop.models.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = """
        select * from category
        join category_unity cu on category.id = cu.son_id
        where father_id = ?1
        """, nativeQuery = true)
    List<Category> findSubCategoriesById(int id);
    @Query(value = """
        select * from category
        join category_unity cu on category.id = cu.son_id
        where father_id = (select id from category where title = ?1);
        """, nativeQuery = true)
    List<Category> findSubCategoriesByTitle(String title);

    List<Category> findAllBySubCategoriesIsNotEmpty();
}
