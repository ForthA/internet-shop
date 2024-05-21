package ru.tarasov.internetshop.responses;

import lombok.Getter;
import lombok.Setter;
import ru.tarasov.internetshop.models.Product;

import java.util.List;

@Getter
@Setter
public class ProductsResponse {

    private boolean authorized;

    private List<Product> products;

}
