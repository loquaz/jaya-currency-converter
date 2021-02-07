package jaya.currencyconverter.controller;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import io.javalin.http.Context;
import jaya.currencyconverter.config.module.UserServiceAnnotations.UserControllerLogger;
import jaya.currencyconverter.dto.HttpResponseDTO;
import jaya.currencyconverter.dto.UserDTO;
import jaya.currencyconverter.entity.User;
import jaya.currencyconverter.service.UserService;

@Singleton
public class UserController {

    private UserService service;

    @Inject
    @UserControllerLogger
    private Logger userControllerLogger;

    @Inject
    public UserController(UserService service){
        this.service = service;
    }

    /**
     * Signs Up a new API user
     */
    public void signup(Context ctx)  {
 
        try{
            User user = ctx.bodyAsClass(User.class);
            this.service.saveUser(user);
            ctx.status(201);
            ctx.json( new UserDTO( user ) );
        }
        catch (Exception e) {
            this.userControllerLogger.error(e.getMessage());
            ctx.status(400);
            ctx.json(new HttpResponseDTO(400, e.getMessage(),true));
        }
    }
    
}
