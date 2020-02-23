package com.xpadro.testingwebapp;

import org.junit.ClassRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainerProvider;

@ContextConfiguration(initializers = {AbstractInitializerTest.Initializer.class})
public abstract class AbstractInitializerTest {

    @ClassRule
    public static final JdbcDatabaseContainer<?> mySQLContainer = new MySQLContainerProvider()
            .newInstance()
            .withDatabaseName("database-integration-test")
            .withUsername("testuser")
            .withPassword("testpwd");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
