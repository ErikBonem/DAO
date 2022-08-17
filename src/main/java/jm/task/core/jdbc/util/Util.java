package jm.task.core.jdbc.util;

import com.sun.xml.fastinfoset.sax.Properties;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



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
}

