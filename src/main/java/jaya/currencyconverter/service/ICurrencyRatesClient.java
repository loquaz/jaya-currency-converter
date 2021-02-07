package jaya.currencyconverter.service;

import java.util.concurrent.CompletableFuture;

import jaya.currencyconverter.dto.CurrencyRatesDTO;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ICurrencyRatesClient {

    @GET("/latest")
    @Headers("accept: application/json")
    CompletableFuture<CurrencyRatesDTO> getRates(@Query("base") String base);
    
}
