package com.belhard.bookstore.dao.mapper;

import com.belhard.bookstore.dao.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
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
}
