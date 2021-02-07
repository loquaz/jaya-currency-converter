package jaya.currencyconverter.service;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import jaya.currencyconverter.config.module.UserServiceAnnotations.UserServiceLogger;
import jaya.currencyconverter.entity.User;
import jaya.currencyconverter.repository.UserRepository;

@Singleton
public class UserService {

    private UserRepository repository;

    @Inject
    @UserServiceLogger
    private Logger serviceLogger;

    @Inject
    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public User saveUser(User user) throws Exception{

        if(user.getUsername() == null || user.getUsername().isEmpty()){
            serviceLogger.error("username can't be empty");
            throw new Exception("username can't be empty");           
        }
        
        if(user.getPassword() == null || user.getPassword().isEmpty()){
            serviceLogger.error("password can't be empty");
            throw new Exception("password can't be empty");
        }

        try {
            return this.repository.saveUser(user);
        } catch(SQLException e){
            serviceLogger.error("user couldn't be saved to database");
            serviceLogger.error(e.getCause().getMessage());
            throw new Exception(e.getCause().getMessage());
        }
    }
    
}
