package ru.gav19770210.javapro.task05.repositories;

import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import ru.gav19770210.javapro.task05.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO implements AbstractDAO<User, Long> {
    private final String tableName = "users";
    private final String tableID = "id";
    private final DBConnector dbConnector;

    public UserDAO(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @SneakyThrows
    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM " + tableName + " WHERE " + tableID + "=?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(id);
                    user.setName(rs.getString("name"));
                    return Optional.of(user);
                }
                rs.close();
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM " + tableName;
        List<User> list = new ArrayList<>();
        try (Connection connection = dbConnector.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong(tableID));
                user.setName(rs.getString("name"));
                list.add(user);
            }
            rs.close();
        }
        return list;
    }

    @SneakyThrows
    public Optional<User> findByName(String name) {
        String sql = "SELECT * FROM " + tableName + " WHERE name=?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong(tableID));
                    user.setName(rs.getString("name"));
                    return Optional.of(user);
                }
                rs.close();
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    @Override
    public User create(User entity) {
        String sql = "INSERT INTO " + tableName + " (name) VALUES (?)";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getName());
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
    public User update(User entity) {
        String sql = "UPDATE " + tableName + " SET name=? WHERE " + tableID + "=?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getName());
            ps.setLong(2, entity.getId());
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                return entity;
            }
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void delete(User entity) {
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
