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
import jaya.currencyconverter.controller.CurrencyConversionController;
import jaya.currencyconverter.dto.TransactionDTO;

@TestMethodOrder(OrderAnnotation.class)
public class ConversionUnitTest {

    private static Context ctx;
    private static CurrencyConversionController controller;
    
    @BeforeAll
    static public void boot(){
        Injector injector = Guice.createInjector( ServerModule.test() );
        controller = injector.getInstance(CurrencyConversionController.class);
    }

    @BeforeEach
    public void configureTestCase(){
        ctx = mock(Context.class);
    } 

    @Test
    @Order(1)
    public void POST_create_a_valid_transaction(){

        when(ctx.bodyAsClass(TransactionDTO.class)).thenReturn(new TransactionDTO());
        controller.doConversion(ctx);
        verify(ctx).status(201);

    }
    
}
