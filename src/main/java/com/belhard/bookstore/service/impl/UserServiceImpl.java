package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.entity.User;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    public UserServiceImpl() {
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private UserDao userDao;
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public List<UserDto> getAllUser() {
        logger.debug("Start method service - getAllUser");
        List<User> users = userDao.getAllUser();
        return users.stream().map(entity -> toDto(entity))
                .collect(Collectors.toList());
    }

    private UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setLastName(entity.getLastName());
        dto.setFirstName(entity.getFirstName());
        dto.setEmail(entity.getEmail());
        dto.setLogin(entity.getLogin());
        dto.setPassword(entity.getPassword());
        dto.setAge(entity.getAge());
        dto.setRole(UserDto.RoleDto.valueOf(entity.getRole().toString()));
        return dto;
    }

    @Override
    public UserDto getUserById(Long id) {
        logger.debug("Start method service - getUserById(");
        User user = userDao.getUserById(id);
        if (user == null) {
            throw new RuntimeException("Can not find user with Id = " + id);
        }
        return toDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.debug("Start method service - getUserByEmail");
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("Can not find user with Email: " + email);
        }
        return toDto(user);
    }


    @Override
    public List<UserDto> getUserByLastname(String lastName) {
        logger.debug("Start method service - getUserByLastname");
        List<User> users = userDao.getUserByLastname(lastName);
        return users.stream().map(entity -> toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.debug("Start method service - createUser");
        User existing = userDao.getUserByEmail(userDto.getEmail());
        if (existing != null) {
            throw new RuntimeException("User with Email: " + userDto.getEmail() + " exists");
        }
        User newUser = toUser(userDto);
        User createdUser = userDao.createUser(newUser);
        UserDto createdUserDto = toDto(createdUser);
        return createdUserDto;
    }

    private User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setLastName(userDto.getLastName());
        user.setFirstName(userDto.getFirstName());
        user.setEmail(userDto.getEmail());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setAge(userDto.getAge());
        user.setRole(User.Roles.valueOf(userDto.getRole().toString()));
        return user;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        logger.debug("Start method service - updateUser");
        User existing = userDao.getUserByEmail(userDto.getEmail());
        if (existing != null && existing.getId() != userDto.getId()) {
            throw new RuntimeException("Book with Isbn exists");
        }
        User newUser = toUser(userDto);
        User receivedUser = userDao.updateUser(newUser);
        UserDto updatedUserDto = toDto(receivedUser);
        return updatedUserDto;
    }

    @Override
    public void deleteUser(Long id) {
        logger.debug("Start method service - deleteUser");
        if (!userDao.deleteUser(id)) {
            throw new RuntimeException("Book didn't delete");
        }
    }

    @Override
    public int countAllUsers() {
        logger.debug("Start method service - countAllUsers");
        return userDao.countAllUsers();
    }


}
