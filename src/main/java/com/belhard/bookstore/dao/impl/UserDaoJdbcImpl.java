package com.belhard.bookstore.dao.impl;

import com.belhard.bookstore.dao.UserDao;
import com.belhard.bookstore.dao.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("userDao")
public class UserDaoJdbcImpl implements UserDao {

    @PersistenceContext
    private EntityManager manager;

    private static final Logger logger = LogManager.getLogger(UserDaoJdbcImpl.class);

    public static final String GET_USER_BY_EMAIL = "select u from User u where u.email =?1 and deleted = false";
    public static final String GET_USER_BY_LASTNAME = "select u from User u where u.lastName =?1 and deleted = false";
    public static final String DELETE_USER = "update User u set deleted = true where u.id =?1 and deleted = false";
    public static final String COUNT_USER = "select count(u) FROM users u where u.deleted = false";

    @Override
    public List<User> getAllUser() {
        List<User> users = manager.createQuery("from User where deleted = false", User.class).getResultList();
        return users;
        //logger.debug("Database query GET_ALL");
        //logger.error("error in method - getAllUser");
    }

    @Override
    public User getUserById(Long id) {
        User user = manager.find(User.class, id);
        return user;
        //logger.debug("Database query GET_USER_BY_ID");
        //logger.error("error in method - getUserById");
    }

    @Override
    public User getUserByEmail(String email) {
        User user = (User) manager.createQuery(GET_USER_BY_EMAIL).setParameter(1, email).getSingleResult();
        return user;
        //logger.debug("Database query GET_USER_BY_EMAIL");
        //logger.error("error in method - getUserByEmail");
    }

    @Override
    public List<User> getUserByLastname(String lastName) {
        List<User> users = manager.createQuery(GET_USER_BY_LASTNAME).setParameter(1, lastName).getResultList();
        return users;
        //logger.debug("Database query GET_USER_BY_LASTNAME");
        //logger.error("error in method - getUserByLastname");
    }

    @Override
    public User createUser(User user) {
        manager.persist(user);
        return user;
        //logger.debug("Database query CREATE_USER");
        //logger.error("error in method - createUser");
    }

    @Override
    public User updateUser(User user) {
        manager.merge(user);
        return user;
        // logger.debug("Database query UPDATE_USER");
        // logger.error("error in method - updateUser");
    }

    @Override
    public boolean deleteUser(Long id) {
        int r = manager.createQuery(DELETE_USER).setParameter(1, id).executeUpdate();
        if (r != 1) {
            throw new RuntimeException("Can't delete user with id: " + id);
        }
        return true;
        //logger.debug("Database query DELETE_USER");
        //logger.error("error in method - deleteUser");
    }

    @Override
    public int countAllUsers() {
        int rowQv = manager.createQuery(COUNT_USER).executeUpdate();
        manager.clear();
        return rowQv;
        //logger.debug("Database query COUNT_USER");
        //logger.error("error in method - countAllUsers");
    }
}
