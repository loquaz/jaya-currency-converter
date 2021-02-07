package jaya.currencyconverter.service;

import java.util.Date;

import jaya.currencyconverter.dto.CurrencyRatesDTO;

public interface IHttpClientService {
    
    CurrencyRatesDTO getRates(String base, Date start, Date end);
    
}
