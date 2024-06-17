package ru.tarasov.internetshop.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tarasov.internetshop.models.Product;
import ru.tarasov.internetshop.services.CategoryService;
import ru.tarasov.internetshop.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @Operation(summary = "Все продукты", tags = {"list"})
    @GetMapping("/list")
    public ResponseEntity<List<Product>> listPage(){
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Получить конкретный продукт", tags = {"find"})
    @GetMapping("/{id}")
    public ResponseEntity<Product> productPage(@PathVariable int id){
        Product product = productService.findProductById(id);
        if (product == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
