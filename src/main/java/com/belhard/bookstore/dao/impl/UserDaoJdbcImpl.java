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

    public static final String COUNT_USER = "SELECT COUNT(*) AS count FROM users WHERE deleted = false";

    @Override
    public List<User> getAllUser() {
        List<User> users = manager.createQuery("from User", User.class).getResultList();
        manager.clear();
        return users;
        //logger.debug("Database query GET_ALL");
        //logger.error("error in method - getAllUser");
    }

    @Override
    public User getUserById(Long id) {
        User user = manager.find(User.class, id);
        manager.clear();
        return user;
        //logger.debug("Database query GET_USER_BY_ID");
        //logger.error("error in method - getUserById");
    }

    @Override
    public User getUserByEmail(String email) {
        return manager.find(User.class, email);

        //logger.debug("Database query GET_USER_BY_EMAIL");
        //logger.error("error in method - getUserByEmail");
    }

    @Override
    public List<User> getUserByLastname(String lastName) {
        List<User> users = manager.createQuery("from User where lastName = ?1").setParameter(1, lastName).getResultList();
        manager.clear();
        return users;
        //logger.debug("Database query GET_USER_BY_LASTNAME");
        //logger.error("error in method - getUserByLastname");
    }

    @Override
    public User createUser(User user) {
        manager.persist(user);
        manager.clear();
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
        int r = manager.createQuery("delete from User where id = ?1").setParameter(1, id).executeUpdate();
        if (r != 1) {
            throw new RuntimeException("Can't delete user with id: " + id);
        }
        return true;
        //logger.debug("Database query DELETE_USER");
        //logger.error("error in method - deleteUser");
    }//

    @Override
    public int countAllUsers() {
        int rowQv = manager.createNativeQuery(COUNT_USER).executeUpdate();
        manager.clear();
        return rowQv;
        //logger.debug("Database query COUNT_USER");
        //logger.error("error in method - countAllUsers");
    }
}
