package ru.gav19770210.javapro.task06.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gav19770210.javapro.task06.entities.Product;
import ru.gav19770210.javapro.task06.services.ProductService;

import java.util.List;

@RestController
@RequestMapping(value = "product", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/{id}/get")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        var productGet = productService.getProductById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productGet);
    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<List<Product>> getAllProducts() {
        var products = productService.getAllProducts();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(products);
    }

    @GetMapping(value = "/{user_id}/get-by-user")
    public ResponseEntity<List<Product>> getProductByUser(@PathVariable("user_id") Long user_id) {
        var products = productService.getUserProducts(user_id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(products);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@RequestBody @Validated Product product) {
        var productCreate = productService.createProduct(product);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productCreate);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@RequestBody @Validated Product product) {
        var productUpdate = productService.updateProduct(product);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productUpdate);
    }

    @DeleteMapping(value = "/{id}/delete")
    public HttpStatus deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return HttpStatus.OK;
    }
}
