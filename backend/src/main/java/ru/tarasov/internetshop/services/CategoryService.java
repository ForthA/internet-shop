package ru.tarasov.internetshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tarasov.internetshop.models.Category;
import ru.tarasov.internetshop.repositories.CategoryRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findSubCategoriesById(int id){
        return categoryRepository.findSubCategoriesById(id);
    }

    public List<Category> findSubCategoriesByTitle(String title){
        return categoryRepository.findSubCategoriesByTitle(title);
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public List<Category> findAllBySubCategoriesIsNotEmpty() {
        return categoryRepository.findAllBySubCategoriesIsNotEmpty();
    }
}
