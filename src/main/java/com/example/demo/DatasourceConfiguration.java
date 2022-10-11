package com.example.demo;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.lod")
    public DataSourceProperties lodDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.ckan")
    public DataSourceProperties ckanDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource lodDataSource() {
        return lodDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public DataSource ckanDataSource() {
        return ckanDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public JdbcTemplate lodJdbcTemplate(@Qualifier("lodDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate ckanJdbcTemplate(@Qualifier("ckanDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}