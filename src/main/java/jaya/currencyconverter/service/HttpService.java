package jaya.currencyconverter.service;

import java.util.Date;

import javax.inject.Inject;

import jaya.currencyconverter.dto.CurrencyRatesDTO;

public class HttpService {

    private IHttpClientService httpClient;

    @Inject
    public HttpService(IHttpClientService httpClient){
        this.httpClient = httpClient;
    }

    public CurrencyRatesDTO getRates(String base, Date start, Date end){
       return this.httpClient.getRates(base, start, end);
    }
    
}
