package ru.gav19770210.javapro.task06.services;

import ru.gav19770210.javapro.task06.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    List<Product> getUserProducts(Long userId);

    Product getProductById(Long id);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProductById(Long id);
}
