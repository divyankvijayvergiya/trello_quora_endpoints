package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * The method to create a new user from given UserEntity object
     * @param userEntity: object from which new user will be created
     * @return UserEntity object
     * @Author: Vipin P K
     */
    public UserEntity createUser(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return userEntity;
    }

    /**
     * The method to find existing user by user name
     * @param userName : will be searched in database for existing user
     * @return UserEntity object if user with requested user name exists in database
     * @Author: Vipin P K
     */
    public UserEntity getUserByUserName(final String userName) {
        try {
            return entityManager.createNamedQuery("userByUserName", UserEntity.class).setParameter("userName", userName).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /**
     * The method to find existing user by email id
     * @param email: will be searched in database for existing user
     * @return UserEntity object if user with requested email id exists in database
     */
    public UserEntity getUserByEmail(final String email) {
        try {
            return entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}