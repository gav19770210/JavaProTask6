package ru.gav19770210.javapro.task05.services;

import org.springframework.stereotype.Service;
import ru.gav19770210.javapro.task05.repositories.ProductDAO;
import ru.gav19770210.javapro.task05.repositories.UserDAO;
import ru.gav19770210.javapro.task05.entities.Product;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDAO productDAO;
    private final UserDAO userDao;

    public ProductServiceImpl(ProductDAO productDAO, UserDAO userDao) {
        this.productDAO = productDAO;
        this.userDao = userDao;
    }

    @Override
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    @Override
    public List<Product> getUserProducts(Long userId) {
        return productDAO.getAllByUserId(userId);
    }

    @Override
    public Product getProductById(Long id) {
        return productDAO.findById(id).orElseThrow(() -> new NoSuchElementException("Не найден продукт с id = " + id));
    }

    @Override
    public Product createProduct(Product product) {
        if (userDao.findById(product.getUserId()).isEmpty()) {
            throw new NoSuchElementException("Не найден пользователь с id = " + product.getUserId());
        }
        if (productDAO.findByAccountNumber(product.getAccountNumber()).isPresent()) {
            throw new IllegalArgumentException("Уже существует продукт с account_number = " + product.getAccountNumber());
        }
        return productDAO.create(product);
    }

    @Override
    public Product updateProduct(Product product) {
        var productById = productDAO.findById(product.getId())
                .orElseThrow(() -> new NoSuchElementException("Не найден продукт с id = " + product.getId()));

        if (userDao.findById(product.getUserId()).isEmpty()) {
            throw new NoSuchElementException("Не найден пользователь с id = " + product.getUserId());
        }
        var productByAccount = productDAO.findByAccountNumber(product.getAccountNumber()).orElse(null);
        if (productByAccount != null && !productByAccount.getId().equals(product.getId())) {
            throw new IllegalArgumentException("Уже существует продукт с account_number = " + product.getAccountNumber());
        }
        if (!productById.equals(product)) {
            return productDAO.update(product);
        }
        return productById;
    }

    @Override
    public void deleteProductById(Long id) {
        if (productDAO.findById(id).isEmpty()) {
            throw new NoSuchElementException("Не найден продукт с id = " + id);
        }
        productDAO.deleteById(id);
    }
}
