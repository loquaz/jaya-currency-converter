package jaya.currencyconverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import io.javalin.Javalin;
import jaya.currencyconverter.config.module.ServerModule;
import jaya.currencyconverter.controller.CurrencyConversionController;
import jaya.currencyconverter.controller.UserController;
import jaya.currencyconverter.dto.HttpResponseDTO;
import jaya.currencyconverter.dto.TransactionDTO;
import jaya.currencyconverter.entity.User;
import jaya.currencyconverter.repository.UserRepository;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

@TestMethodOrder(OrderAnnotation.class)
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
    @Order(1)
    public void GET_fetch_transactions_returns_an_empty_list(){

        HttpResponse res                    = Unirest.get("http://localhost:5000/api/transactions-by-user?userID=1").asJson();
        Gson g                              = new Gson();
        HttpResponseDTO resDTO              = g.fromJson(res.getBody().toString(), HttpResponseDTO.class); 
        ArrayList<TransactionDTO> emptyList = new ArrayList<TransactionDTO>();
        
        assertEquals(res.getStatus(),200);
        assertEquals(resDTO.getData(), emptyList);
        
    }

    @Test
    @Order(2)
    public void GET_fetch_transactions_returns_an_list_with_one_transaction(){

        /*
         * signup an user first
         */
        HttpResponse userSignupRes    =   Unirest.post("http://localhost:5000/api/signup").body(
            "{\"username\": \"adailton\",\"password\": \"123456\"}"
        ).asJson();

        Gson g                  = new Gson();        
        HttpResponseDTO resDTO  = g.fromJson(userSignupRes.getBody().toString(), HttpResponseDTO.class); 
        User registeredUser     = g.fromJson(resDTO.getData().toString(), User.class);
        
        /*
         * Try to do the conversion
         */
        Unirest.post("http://localhost:5000/api/convert").body(
             buildTransactionJson(registeredUser.getId(), "BRL", "USD", 52)
        ).asJson();
        
        /*
         * retrieves the users transactions [must exists just one]
         */
        HttpResponse userTransactiosRes         = Unirest.get("http://localhost:5000/api/transactions-by-user?userID="+registeredUser.getId()).asJson();
        HttpResponseDTO userTransactionsResDTO  = g.fromJson(userTransactiosRes.getBody().toString(), HttpResponseDTO.class); 
        
        assertEquals(userTransactiosRes.getStatus(),200);
        assertEquals( ((ArrayList) userTransactionsResDTO.getData()).size(), 1);
        
    }

    private String buildTransactionJson(int userID, String from, String to, int amount){
        String body = "{\"userID\":"+userID+",";
        body += "\"currencyFrom\":\""+from+"\",";
        body += "\"currencyTo\":\""+to+"\",";
        body += "\"amount\":"+amount+"}";
        return body;
    }

}
