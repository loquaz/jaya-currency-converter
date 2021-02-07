package jaya.currencyconverter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.SQLException;

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
import jaya.currencyconverter.controller.CurrencyConversionController;
import jaya.currencyconverter.controller.UserController;
import jaya.currencyconverter.dto.TransactionDTO;
import jaya.currencyconverter.entity.User;
import jaya.currencyconverter.repository.UserRepository;

@TestMethodOrder(OrderAnnotation.class)
public class ConversionUnitTest {

    private static Context ctx;
    private static Context userctx;
    private static CurrencyConversionController controller;
    private static UserController userController;
    private static User user;
    private static UserRepository userRepo;
    
    @BeforeAll
    static public void boot(){
        Injector injector = Guice.createInjector( ServerModule.test() );
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

    @BeforeEach
    public void configureTestCase(){
        ctx = mock(Context.class);
        userctx = mock(Context.class);
    } 

    @Test
    @Order(1)
    public void POST_create_a_valid_transaction(){

        /*
         * A Valid transaction must have the following fields:
         * - user ID
         * - currency from
         * - amount
         * - currency to (the value will be converted to this)
         * - rate
         * - date/hour 
         */
        TransactionDTO transaction = new TransactionDTO();
        transaction.setUserID(user.getId());
        transaction.setCurrencyFrom("BRL");
        transaction.setCurrencyTo("USD");
        transaction.setAmount(new BigDecimal(53));
        
        when(ctx.bodyAsClass(TransactionDTO.class)).thenReturn( transaction );
        controller.doConversion(ctx);
        verify(ctx).status(201);

    }

    @Test
    @Order(2)
    public void POST_do_not_create_n_transaction_without_currency_from(){

        TransactionDTO transaction = new TransactionDTO();
        transaction.setUserID(user.getId());
        transaction.setCurrencyFrom( null ); //invalid value
        transaction.setCurrencyTo("USD");
        transaction.setAmount(new BigDecimal(53));
        
        when(ctx.bodyAsClass(TransactionDTO.class)).thenReturn( transaction );
        controller.doConversion(ctx);
        verify(ctx).status(400);

    }

    @Test
    @Order(3)
    public void POST_do_not_create_n_transaction_without_currency_to(){

        TransactionDTO transaction = new TransactionDTO();
        transaction.setUserID(user.getId());
        transaction.setCurrencyFrom( "USD" ); 
        transaction.setCurrencyTo(""); //invalid value
        transaction.setAmount(new BigDecimal(53));
        
        when(ctx.bodyAsClass(TransactionDTO.class)).thenReturn( transaction );
        controller.doConversion(ctx);
        verify(ctx).status(400);

    }

    @Test
    @Order(4)
    public void POST_do_not_create_a_transaction_with_invalid_user_id(){

        TransactionDTO transaction = new TransactionDTO();
        transaction.setUserID( 2 );
        transaction.setCurrencyFrom( "EUR" ); 
        transaction.setCurrencyTo("BRL");
        transaction.setAmount(new BigDecimal(53));
        
        when(ctx.bodyAsClass(TransactionDTO.class)).thenReturn( transaction );
        controller.doConversion(ctx);
        verify(ctx).status(400);

    }

    @Test
    @Order(5)
    public void POST_do_not_create_a_transaction_without_amount(){

        TransactionDTO transaction = new TransactionDTO();
        transaction.setUserID( user.getId() );
        transaction.setCurrencyFrom( "EUR" ); 
        transaction.setCurrencyTo("BRL");
        transaction.setAmount( null ); // invalid amount
        
        when(ctx.bodyAsClass(TransactionDTO.class)).thenReturn( transaction );
        controller.doConversion(ctx);
        verify(ctx).status(400);

    }
    
}
