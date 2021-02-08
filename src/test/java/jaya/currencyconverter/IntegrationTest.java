package jaya.currencyconverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import jaya.currencyconverter.config.module.ServerModule;
import jaya.currencyconverter.controller.CurrencyConversionController;
import jaya.currencyconverter.controller.UserController;
import jaya.currencyconverter.entity.User;
import jaya.currencyconverter.repository.UserRepository;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class IntegrationTest {
    
    private static Injector injector;
    private static Javalin app;
    private static CurrencyConversionController controller;
    private static UserController userController;
    private static UserRepository userRepo;
    private static User user;

    @BeforeAll
    static public void boot(){
        injector = Guice.createInjector( ServerModule.test() );
        app         = injector.getInstance(WebEntryPoint.class).boot();
        controller = injector.getInstance(CurrencyConversionController.class);
        userController = injector.getInstance(UserController.class);
        userRepo = injector.getInstance(UserRepository.class);

        /* sign up an user */
        user = new User("sereno", "123");
        try{
            userRepo.saveUser(user);
        }catch(SQLException e){

        }
    }

    @Test
    public void GET_fetch_transactions_returns_valid_list(){

        HttpResponse res = Unirest.get("http://localhost:5000/api/transactions-by-user?userID=1").asJson();
        assertEquals(res.getStatus(),200);
    }

}
