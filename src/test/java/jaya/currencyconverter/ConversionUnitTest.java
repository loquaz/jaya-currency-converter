package jaya.currencyconverter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

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

@TestMethodOrder(OrderAnnotation.class)
public class ConversionUnitTest {

    private static Context ctx;
    private static Context userctx;
    private static CurrencyConversionController controller;
    private static UserController userController;
    
    @BeforeAll
    static public void boot(){
        Injector injector = Guice.createInjector( ServerModule.test() );
        controller = injector.getInstance(CurrencyConversionController.class);
        userController = injector.getInstance(UserController.class);
    }

    @BeforeEach
    public void configureTestCase(){
        ctx = mock(Context.class);
        userctx = mock(Context.class);
    } 

    @Test
    @Order(1)
    public void POST_create_a_valid_transaction(){

        /* sign up an user */
        User user = new User("sereno", "123");
        when(userctx.bodyAsClass(User.class)).thenReturn(user);
        userController.signup(userctx);
        verify(userctx).status(201);

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
    
}
