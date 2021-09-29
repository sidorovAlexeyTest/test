package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static Connection connection;
    private static SessionFactory sessionFactory;

    private static final String URL = "jdbc:mysql://localhost/test?serverTimezone=UTC&useSSL=false";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        if(connection != null) {
            return connection;
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory != null) {
            return sessionFactory;
        }

        StandardServiceRegistryBuilder registryBuilder
                = new StandardServiceRegistryBuilder();
        registryBuilder
                .applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect")
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .applySetting("hibernate.connection.url", "jdbc:mysql://localhost/test")
                .applySetting("hibernate.connection.serverTimezone", "UTC")
                .applySetting("hibernate.connection.username", USER)
                .applySetting("hibernate.connection.password", PASSWORD);
        ServiceRegistry serviceRegistry = registryBuilder.build();

        return new Configuration()
                .addAnnotatedClass(User.class)
                .buildSessionFactory(serviceRegistry);
    }
}
