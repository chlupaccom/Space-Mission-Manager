package cz.muni.fi.services.impl;

import cz.muni.fi.dao.UserDao;
import cz.muni.fi.entity.User;
import cz.muni.fi.services.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the {@link UserService}. This class is part of the
 * service module of the application that provides the implementation of the
 * business logic (main logic of the application).
 *
 * @author Vojtech Bruza
 */
@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public void acceptAssignedMission(User user){
        if(user == null){
            throw new IllegalArgumentException("User must not be null.");
        }
        if(user.missionStatusPending()){
            user.setAcceptedMission(true);
        } else {
            throw new IllegalArgumentException("This user does not have pending mission status.");
        }
        try {
            userDao.updateUser(user);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not update user.", e);
        }
    }

    @Override
    public void rejectAssignedMission(User user, String explanation) throws IllegalArgumentException{
        if(user == null){
            throw new IllegalArgumentException("User must not be null.");
        }
        if(explanation == null){
            throw new IllegalArgumentException("Explanation must not be null.");
        }
        if(explanation.isEmpty()){
            throw new IllegalArgumentException("Explanation must not be empty.");
        }
        if(user.missionStatusPending()){
            user.setExplanation(explanation);
            user.setMission(null);
        } else {
            throw new IllegalArgumentException("This user does not have pending mission status.");
        }
        try {
            userDao.updateUser(user);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not update user.", e);
        }
    }

    @Override
    public void addUser(User user) throws DataAccessException {
        if(user == null){
          throw new IllegalArgumentException("User must not be null.");
        }
        try {
            userDao.addUser(user);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not add user.", e);
        }
    }

    @Override
    public void updateUser(User user) throws DataAccessException {
        if(user == null){
            throw new IllegalArgumentException("User must not be null.");
        }
        try {
            userDao.updateUser(user);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not update user.", e);
        }
    }

    @Override
    public void deleteUser(User user) throws DataAccessException {
        if(user == null){
            throw new IllegalArgumentException("User must not be null.");
        }
        try {
            userDao.deleteUser(user);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not delete user.", e);
        }
    }

    @Override
    public List<User> findAllUsers() throws DataAccessException {
        try {
            return Collections.unmodifiableList(userDao.findAllUsers());
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not find users.", e);
        }
    }

    @Override
    public List<User> findAllAstronauts() throws DataAccessException {
        try {
            return Collections.unmodifiableList(userDao.findAllAstronauts());
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not find astronauts.", e);
        }
    }

    @Override
    public User findUserById(Long id) throws DataAccessException {
        if(id == null){
            throw new IllegalArgumentException("Id must not be null.");
        }
        try {
            return userDao.findUserById(id);
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not find user.", e);
        }
    }

    @Override
    public List<User> findAllAvailableAstronauts() throws DataAccessException {
        try {
            return Collections.unmodifiableList(userDao.findAllAvailableAstronauts());
        } catch (Throwable e) {
            throw new ServiceDataAccessException("Could not find astronauts.", e);
        }
    }
}
