package ru.gav19770210.javapro.task05.repositories;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.gav19770210.javapro.task05.entities.Product;
import ru.gav19770210.javapro.task05.entities.ProductType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDAO implements AbstractDAO<Product, Long> {
    private final String tableName = "products";
    private final String tableID = "id";
    @Autowired
    private final DBConnector dbConnector;

    public ProductDAO(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @SneakyThrows
    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM " + tableName + " WHERE " + tableID + "=?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setId(id);
                    product.setAccountNumber(rs.getString("account_number"));
                    product.setBalance(rs.getBigDecimal("balance"));
                    product.setType(ProductType.valueOf(rs.getString("type")));
                    product.setUserId(rs.getLong("user_id"));
                    return Optional.of(product);
                }
                rs.close();
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    public Optional<Product> findByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM " + tableName + " WHERE account_number=?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getLong(tableID));
                    product.setAccountNumber(rs.getString("account_number"));
                    product.setBalance(rs.getBigDecimal("balance"));
                    product.setType(ProductType.valueOf(rs.getString("type")));
                    product.setUserId(rs.getLong("user_id"));
                    return Optional.of(product);
                }
                rs.close();
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM " + tableName;
        List<Product> list = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong(tableID));
                product.setAccountNumber(rs.getString("account_number"));
                product.setBalance(rs.getBigDecimal("balance"));
                product.setType(ProductType.valueOf(rs.getString("type")));
                product.setUserId(rs.getLong("user_id"));
                list.add(product);
            }
            rs.close();
        }
        return list;
    }

    @SneakyThrows
    public List<Product> getAllByUserId(Long id) {
        String sql = "SELECT * FROM " + tableName + " WHERE user_id=?";
        List<Product> list = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getLong(tableID));
                    product.setAccountNumber(rs.getString("account_number"));
                    product.setBalance(rs.getBigDecimal("balance"));
                    product.setType(ProductType.valueOf(rs.getString("type")));
                    product.setUserId(rs.getLong("user_id"));
                    list.add(product);
                }
                rs.close();
            }
        }
        return list;
    }

    @SneakyThrows
    @Override
    public Product create(Product entity) {
        String sql = "INSERT INTO " + tableName + " (account_number,balance,type,user_id) VALUES (?,?,?,?)";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getAccountNumber());
            ps.setBigDecimal(2, entity.getBalance());
            ps.setString(3, entity.getType().name());
            ps.setLong(4, entity.getUserId());
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(1));
                    }
                    generatedKeys.close();
                }
                return entity;
            }
        }
        return null;
    }

    @SneakyThrows
    @Override
    public Product update(Product entity) {
        String sql = "UPDATE " + tableName + " SET account_number=?, balance=?, type=?, user_id=? WHERE " + tableID + "=?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getAccountNumber());
            ps.setBigDecimal(2, entity.getBalance());
            ps.setString(3, entity.getType().name());
            ps.setLong(4, entity.getUserId());
            ps.setLong(5, entity.getId());
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                return entity;
            }
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void delete(Product entity) {
        deleteById(entity.getId());
    }

    @SneakyThrows
    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM " + tableName + " WHERE " + tableID + "=?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int affectedRows = ps.executeUpdate();
        }
    }
}