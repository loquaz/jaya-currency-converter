package jaya.currencyconverter.service;

import java.util.Date;

import jaya.currencyconverter.dto.CurrencyRates;

public interface IHttpClientService {
    
    CurrencyRates getRates(String base, Date start, Date end);
    
}
