package com.belhard.bookstore.service.impl;

import com.belhard.bookstore.dao.entity.User;
import com.belhard.bookstore.dao.repository.UserRepository;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public List<UserDto> getAllUser(Pageable pageable) {
        logger.debug("Start method service - getAllUser");
        Iterable<User> users = userRepository.findAllUsers(pageable);
        List<User> userList = new ArrayList<>();
        users.forEach(userList::add);
        return userList.stream().map(entity -> toDto(entity))
                .collect(Collectors.toList());
    }

    public UserDto toDto(User entity) {
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
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("No user with id: " + id);
        }
        return toDto(userOptional.get());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.debug("Start method service - getUserByEmail");
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("Can not find user with Email: " + email);
        }
        return toDto(user);
    }


    @Override
    public List<UserDto> getUserByLastname(String lastName) {
        logger.debug("Start method service - getUserByLastname");
        List<User> users = userRepository.findUserByLastName(lastName);
        return users.stream().map(entity -> toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.debug("Start method service - createUser");
        /*User existing = userDao.getUserByEmail(userDto.getEmail());
        if (existing != null) {
            throw new RuntimeException("User with Email: " + userDto.getEmail() + " exists");
        }*/
        User newUser = toUser(userDto);
        User createdUser = userRepository.save(newUser);
        UserDto createdUserDto = toDto(createdUser);
        return createdUserDto;
    }

    public User toUser(UserDto userDto) {
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
        /*User existing = userDao.getUserByEmail(userDto.getEmail());
        if (existing != null && existing.getId() != userDto.getId()) {
            throw new RuntimeException("Book with Isbn exists");
        }*/
        User newUser = toUser(userDto);
        User receivedUser = userRepository.save(newUser);
        UserDto updatedUserDto = toDto(receivedUser);
        return updatedUserDto;
    }

    @Override
    public void deleteUser(Long id) {
        logger.debug("Start method service - deleteUser");
        userRepository.delUser(id);
    }

    @Override
    public int countAllUsers() {
        logger.debug("Start method service - countAllUsers");
        return (int) userRepository.count();
    }
}
