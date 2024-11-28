package ru.gav19770210.javapro.task06.repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.Properties;

public class DBConnector {
    private final HikariConfig config;
    private final HikariDataSource dataSource;

    public DBConnector(Properties properties) {
        this.config = new HikariConfig(properties);
        this.dataSource = new HikariDataSource(config);
    }

    @SneakyThrows
    public Connection getConnection() {
        Class.forName(config.getDriverClassName());
        Connection connection = dataSource.getConnection();
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return connection;
    }
}
