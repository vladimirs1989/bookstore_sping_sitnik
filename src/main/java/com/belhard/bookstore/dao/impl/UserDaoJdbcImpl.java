package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.mapper.UserRowMapper;
import com.belhard.bookstore.dao.dbconfig.DbConfigurator;
import com.belhard.bookstore.dao.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("userDao")
public class UserDaoJdbcImpl implements UserDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserRowMapper rowMapper;

    @Autowired
    public UserDaoJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate, UserRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    private static final Logger logger = LogManager.getLogger(UserDaoJdbcImpl.class);

    public static final String GET_ALL = "SELECT u.id, u.lastName, u.firstName, u.email, u.login, u.password, u.age, r.role AS role FROM users u JOIN roles r ON u.roles_id = r.id WHERE u.deleted = false ORDER BY u.id";
    public static final String GET_USER_BY_ID = "SELECT u.id, u.lastName, u.firstName, u.email, u.login, u.password, u.age, r.role AS role FROM users u JOIN roles r ON u.roles_id = r.id WHERE u.id = :id AND u.deleted = false";
    public static final String GET_USER_BY_EMAIL = "SELECT u.id, u.lastName, u.firstName, u.email, u.login, u.password, u.age, r.role AS role FROM users u JOIN roles r ON u.roles_id = r.id WHERE u.email = :email AND u.deleted = false";
    public static final String GET_USER_BY_LASTNAME = "SELECT u.id, u.lastName, u.firstName, u.email, u.login, u.password, u.age, r.role AS role FROM users u JOIN roles r ON u.roles_id = r.id WHERE u.lastName = :lastName AND u.deleted = false";
    public static final String CREATE_USER = "INSERT INTO users (lastName, firstName, email, login, password, age, roles_id)  VALUES ( :lastName, :firstName, :email, :login, :password, :age, (SELECT id FROM roles WHERE role = :role))";
    public static final String UPDATE_USER = "UPDATE users SET lastName = :lastName, firstName = :firstName, email = :email, login = :login, password = :password, age = :age, roles_id = (SELECT id FROM roles WHERE role = :role) WHERE id = :id AND deleted = false";
    public static final String DELETE_USER = "UPDATE users SET deleted = true WHERE id = :id AND deleted = false";
    public static final String COUNT_USER = "SELECT COUNT(*) AS count FROM users WHERE deleted = false";

    @Override
    public List<User> getAllUser() {
        return jdbcTemplate.query(GET_ALL, rowMapper);
        //logger.debug("Database query GET_ALL");
        //logger.error("error in method - getAllUser");
    }

    @Override
    public User getUserById(Long id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID, Map.of("id", id), rowMapper);
        //logger.debug("Database query GET_USER_BY_ID");
        //logger.error("error in method - getUserById");
    }

    @Override
    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL, Map.of("email", email), rowMapper);
        //logger.debug("Database query GET_USER_BY_EMAIL");
        //logger.error("error in method - getUserByEmail");
    }

    @Override
    public List<User> getUserByLastname(String lastName) {
        return jdbcTemplate.query(GET_USER_BY_LASTNAME, Map.of("lastName", lastName), rowMapper);
        //logger.debug("Database query GET_USER_BY_LASTNAME");
        //logger.error("error in method - getUserByLastname");
    }

    @Override
    public User createUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("lastName", user.getLastName());
        params.put("firstName", user.getFirstName());
        params.put("email", user.getEmail());
        params.put("login", user.getLogin());
        params.put("password", user.getPassword());
        params.put("age", user.getAge());
        params.put("role", user.getRole().toString());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdated = jdbcTemplate.update(CREATE_USER, source, keyHolder, new String[]{"id"});
        if (rowsUpdated != 1) {
            throw new RuntimeException("Can't create user: " + user);
        }
        Long id = Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new RuntimeException("Can't create user: " + user));
        return getUserById(id);
        //logger.debug("Database query CREATE_USER");
        //logger.error("error in method - createUser");
    }

    @Override
    public User updateUser(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("lastName", user.getLastName());
        params.put("firstName", user.getFirstName());
        params.put("email", user.getEmail());
        params.put("login", user.getLogin());
        params.put("password", user.getPassword());
        params.put("age", user.getAge());
        params.put("role", user.getRole().toString());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdated = jdbcTemplate.update(UPDATE_USER, source);
        if (rowsUpdated != 1) {
            throw new RuntimeException("Can't create user: " + user);
        }
        return getUserById(user.getId());
        // logger.debug("Database query UPDATE_USER");
        // logger.error("error in method - updateUser");
    }

    @Override
    public boolean deleteUser(Long id) {
        int result = jdbcTemplate.update(DELETE_USER, Map.of("id", id));
        if (result != 1) {
            throw new RuntimeException("Can't delete user with id: " + id);
        }
        return true;
        //logger.debug("Database query DELETE_USER");
        //logger.error("error in method - deleteUser");
    }//

    @Override
    public int countAllUsers() {
        jdbcTemplate.query(COUNT_USER, rowMapper);
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
