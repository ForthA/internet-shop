package ru.tarasov.internetshop.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.tarasov.internetshop.models.Category;
import ru.tarasov.internetshop.responses.ProductsResponse;
import ru.tarasov.internetshop.services.CategoryService;
import ru.tarasov.internetshop.services.ProductService;

import java.util.List;

@RestController
public class CatalogController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public CatalogController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Operation(summary = "Весь каталог", tags = {"list"})
    @GetMapping("/catalog")
    public ResponseEntity<List<Category>> catalogPage(){
        return new ResponseEntity<>(categoryService.findAllBySubCategoriesIsNotEmptyAndFatherCategoryIsNull(), HttpStatus.OK);
    }

    @Operation(summary = "Все продукты в категории", tags = {"find"})
    @GetMapping("/catalog/categories/{id}")
    public ResponseEntity<ProductsResponse> productsPage(@PathVariable int id){
        ProductsResponse productsResponse = new ProductsResponse();
        productsResponse.setProducts(productService.getAllProductsByCategory(id));
        productsResponse.setAuthorized(SecurityContextHolder.getContext().getAuthentication() != null);
        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }
}
