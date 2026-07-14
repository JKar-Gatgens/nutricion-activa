package com.nutricionactiva.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Construye el {@link DataSource} y corre las migraciones de Flyway sobre
 * el ANTES de devolverlo. Esta version de Spring Boot no trae
 * autoconfiguracion de Flyway (no hay ninguna clase de Flyway en
 * spring-boot-autoconfigure), asi que se dispara aqui explicitamente. Como
 * el bean {@code entityManagerFactory} de Hibernate depende de este
 * DataSource para construirse, el orden (migrar antes de validar el
 * esquema) queda garantizado sin trucos adicionales de ordenamiento.
 */
@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        DataSource dataSource = DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .build();

        Flyway.configure()
                .dataSource(dataSource)
                .load()
                .migrate();

        return dataSource;
    }
}
