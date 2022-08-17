package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final static Connection con = (new Util()).getConnection();
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

        try {
            statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT exists Users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age INT)");
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);

        }
    }

    public void dropUsersTable() {
        try {
            statement = con.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            preparedStatement = con.prepareStatement("INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            preparedStatement = con.prepareStatement("DELETE FROM Users WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);

        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            ResultSet resultSet = con.createStatement().executeQuery("SELECT * FROM Users");
            while (resultSet.next()){
                User user = new User((resultSet.getString("name")), resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            statement = con.createStatement();
            statement.executeUpdate("TRUNCATE TABLE Users");
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
