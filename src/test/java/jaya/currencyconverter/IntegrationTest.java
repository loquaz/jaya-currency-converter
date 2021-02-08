package jaya.currencyconverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import jaya.currencyconverter.config.module.ServerModule;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class IntegrationTest {
    
    private Injector injector   = Guice.createInjector( ServerModule.test() );
    private Javalin app         = injector.getInstance(WebEntryPoint.class).boot();

    @BeforeAll
    

    @Test
    public void GET_fetch_transactions_returns_valid_list(){

        HttpResponse res = Unirest.get("http://localhost:5000/api/transactions-by-user?userID=1").asJson();
        assertEquals(res.getStatus(),200);
    }

}
