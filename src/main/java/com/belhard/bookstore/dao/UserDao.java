package com.belhard.bookstore.dao;

import com.belhard.bookstore.dao.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUser();

    User getUserById(Long id);

    User getUserByEmail(String email);

    List<User> getUserByLastname(String lastName);

    User createUser (User user);

    User updateUser (User user);

    boolean deleteUser (Long id);

    int countAllUsers();
}
