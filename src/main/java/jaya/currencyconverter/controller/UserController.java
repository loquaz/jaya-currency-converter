package jaya.currencyconverter.controller;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiParam;
import io.javalin.plugin.openapi.annotations.OpenApiRequestBody;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;

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
     * Signs Up a new user
     * 
     * This endpoint responds to HTTP POST Methods
     * at the /api/signup path and receives two parameters:
     * - username
     * - password
     */
    @OpenApi(
        path = "/api/user/signup",
        method = HttpMethod.POST,
        summary = "sign up an user",
        pathParams = {
            @OpenApiParam(name = "username", type = String.class, description = "username"),
            @OpenApiParam(name = "password", type = String.class, description = "password"),
        },
        requestBody = @OpenApiRequestBody( content = @OpenApiContent( from = User.class ) ),
        responses = {
            @OpenApiResponse(status = "200", content = @OpenApiContent( from = HttpResponseDTO.class ))
        }
    )
    public void signup(Context ctx)  {
 
        try{
            User user = ctx.bodyAsClass(User.class);
            this.service.saveUser(user);
            ctx.status(201);
            ctx.json( new HttpResponseDTO(201, "new user created", false, new UserDTO( user ) ) );
        }
        catch (Exception e) {
            this.userControllerLogger.error(e.getMessage());
            ctx.status(400);
            ctx.json(new HttpResponseDTO(400, e.getMessage(),true, null));
        }
    }
    
}
