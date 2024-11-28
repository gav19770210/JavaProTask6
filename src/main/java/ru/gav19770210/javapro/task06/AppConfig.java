package ru.gav19770210.javapro.task06;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gav19770210.javapro.task06.repositories.DBConnector;

import java.io.InputStream;
import java.util.Properties;

@Configuration
public class AppConfig {
    @Bean
    @SneakyThrows
    public DBConnector dbConnector() {
        Properties properties = new Properties();
        try (InputStream inputStream = AppConfig.class.getClassLoader()
                .getResourceAsStream("datasource.properties")) {
            properties.load(inputStream);
        }
        return new DBConnector(properties);
    }
}
