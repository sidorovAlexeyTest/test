package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        try {
            connection.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS User(" +
                            "id INT(64) NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                            "name CHAR(25) NOT NULL," +
                            "lastName CHAR(25) NOT NULL," +
                            "age INT(8) NOT NULL" +
                            ")"
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            connection.createStatement().execute(
                    "DROP TABLE IF EXISTS User"
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            if(connection.createStatement().execute(
                    "INSERT INTO User (name, lastName, age) " +
                            "VALUE (\"" + name +
                            "\", \"" + lastName +
                            "\", " + age +
                            ")")) {
                System.out.println("User именем – " + name + " добавлен в базу данных");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            connection.createStatement().execute(
                    "DELETE FROM User WHERE User.id = " + id
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM User");
            int i = 1;
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                list.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try {
            connection.createStatement().execute(
                    "TRUNCATE User"
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
