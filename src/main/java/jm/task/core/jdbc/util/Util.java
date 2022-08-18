package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/testbd";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Qazwazraz12";

    public Connection getConnection(){
        Connection connection;
        try{
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            System.out.println("Connection OK");

        } catch (SQLException e) {
            System.out.println("Soryan, connection ne OK");
            throw new RuntimeException(e);
        }
        return connection;
    }
    public Session getSession(){
        Properties prop = new Properties();
        prop.setProperty("hibernate.connection.url", URL);
        prop.setProperty("hibernate.connection.username", USERNAME);
        prop.setProperty("hibernate.connection.password", PASSWORD);
        prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        prop.setProperty("show_sql", "true");
        SessionFactory sessionFactory = new Configuration().addProperties(prop).addAnnotatedClass(User.class).buildSessionFactory();
        Session session = sessionFactory.openSession();
        return session;
    }
}

