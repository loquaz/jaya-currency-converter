package jaya.currencyconverter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import io.javalin.http.Context;
import jaya.currencyconverter.config.module.ServerModule;
import jaya.currencyconverter.controller.UserController;
import jaya.currencyconverter.entity.User;
 
@TestMethodOrder(OrderAnnotation.class)
public class UserUnitTest {

    private static Context ctx;
    private static UserController userController;
    
    @BeforeAll
    static public void boot(){
        Injector injector = Guice.createInjector( ServerModule.test() );
        userController = injector.getInstance(UserController.class);
    }

    @BeforeEach
    public void configureTestCase(){
        ctx = mock(Context.class);
    } 
    
    @Test
    @Order(1)
    public void POST_create_user_with_valid_params(){
        when(ctx.bodyAsClass(User.class)).thenReturn(new User("sereno", "123456"));
        userController.signup(ctx);
        verify(ctx).status(201);
    }

    @Test
    @Order(2)
    public void POST_do_not_create_user_with_same_credentials_params(){
        when(ctx.bodyAsClass(User.class)).thenReturn(new User("sereno", "123456"));
        userController.signup(ctx);
        verify(ctx).status(400);
    }

    @Test
    @Order(3)
    public void POST_do_not_create_user_without_username(){
        when(ctx.bodyAsClass(User.class)).thenReturn(new User("", "123456"));
        userController.signup(ctx);
        verify(ctx).status(400);
    }

    @Test
    @Order(4)
    public void POST_do_not_create_user_without_password(){
        when(ctx.bodyAsClass(User.class)).thenReturn(new User("Antonio", ""));
        userController.signup(ctx);
        verify(ctx).status(400);
    }
    
}
