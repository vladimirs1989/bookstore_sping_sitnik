package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.dbconfig.DbConfigurator;
import com.belhard.bookstore.dao.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoJdbcImpl.class);

    public static final String GET_ALL = "SELECT u.id, u.lastName, u.firstName, u.email, u.login, u.password, u.age, r.role AS role FROM users u JOIN roles r ON u.roles_id = r.id WHERE u.deleted = false ORDER BY u.id";
    public static final String GET_USER_BY_ID = "SELECT u.id, u.lastName, u.firstName, u.email, u.login, u.password, u.age, r.role AS role FROM users u JOIN roles r ON u.roles_id = r.id WHERE u.id = ? AND u.deleted = false";
    public static final String GET_USER_BY_EMAIL = "SELECT u.id, u.lastName, u.firstName, u.email, u.login, u.password, u.age, r.role AS role FROM users u JOIN roles r ON u.roles_id = r.id WHERE u.email = ? AND u.deleted = false";
    public static final String GET_USER_BY_LASTNAME = "SELECT u.id, u.lastName, u.firstName, u.email, u.login, u.password, u.age, r.role AS role FROM users u JOIN roles r ON u.roles_id = r.id WHERE u.lastName = ? AND u.deleted = false";
    public static final String CREATE_USER = "INSERT INTO users (lastName, firstName, email, login, password, age, roles_id)  VALUES ( ?, ?, ?, ?, ?, ?, (SELECT id FROM roles WHERE role = ?))";
    public static final String UPDATE_USER = "UPDATE users SET lastName = ?, firstName = ?, email = ?, login = ?, password = ?, age = ?, roles_id = (SELECT id FROM roles WHERE role = ?) WHERE id = ? AND deleted = false";
    public static final String DELETE_USER = "UPDATE users SET deleted = true WHERE id = ? AND deleted = false";
    public static final String COUNT_USER = "SELECT COUNT(*) AS count FROM users WHERE deleted = false";

    public UserDaoJdbcImpl() {
        System.out.println("Constructor UserDaoJdbcImpl empty");
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            logger.debug("Database query GET_ALL");
            while (resultSet.next()) {
                User user = processResultSet(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("error in method - getAllUser");
        }
        return users;
    }

    private User processResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setLastName(resultSet.getString("lastName"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setEmail(resultSet.getString("email"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setAge(resultSet.getInt("age"));
        user.setRole(User.Roles.valueOf(resultSet.getString("role")));
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = null;
        try {
            PreparedStatement statement = DbConfigurator.getConnection()
                    .prepareStatement(GET_USER_BY_ID);
            logger.debug("Database query GET_USER_BY_ID");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = processResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("error in method - getUserById");
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;
        try {
            PreparedStatement statement = DbConfigurator.getConnection()
                    .prepareStatement(GET_USER_BY_EMAIL);
            logger.debug("Database query GET_USER_BY_EMAIL");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = processResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("error in method - getUserByEmail");
        }
        return user;
    }

    @Override
    public List<User> getUserByLastname(String lastName) {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = DbConfigurator.getConnection()
                    .prepareStatement(GET_USER_BY_LASTNAME);
            logger.debug("Database query GET_USER_BY_LASTNAME");
            statement.setString(1, lastName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = processResultSet(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("error in method - getUserByLastname");
        }
        return users;
    }

    @Override
    public User createUser(User user) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection()
                    .prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            logger.debug("Database query CREATE_USER");
            statement.setString(1, user.getLastName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());
            statement.setInt(6, user.getAge());
            statement.setString(7, user.getRole().toString());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(("id"));
                return getUserById(id);
            }
        } catch (SQLException e) {
            logger.error("error in method - createUser");
        }
        throw new RuntimeException("Fail in create");
    }

    @Override
    public User updateUser(User user) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection()
                    .prepareStatement(UPDATE_USER);
            logger.debug("Database query UPDATE_USER");
            statement.setString(1, user.getLastName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());
            statement.setInt(6, user.getAge());
            statement.setString(7, user.getRole().toString());
            statement.setLong(8, user.getId());
            statement.executeUpdate();
            return getUserById(user.getId());
        } catch (SQLException e) {
            logger.error("error in method - updateUser");
        }
        throw new RuntimeException("fail update");
    }

    @Override
    public boolean deleteUser(Long id) {
        try {
            PreparedStatement statement = DbConfigurator.getConnection()
                    .prepareStatement(DELETE_USER);
            logger.debug("Database query DELETE_USER");
            statement.setLong(1, id);
            int result = statement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            logger.error("error in method - deleteUser");
        }
        return false;
    }

    @Override
    public int countAllUsers() {
        try {
            Statement statement = DbConfigurator.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(COUNT_USER);
            logger.debug("Database query COUNT_USER");
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            logger.error("error in method - countAllUsers");
        }
        throw new RuntimeException("books not count");
    }
}
