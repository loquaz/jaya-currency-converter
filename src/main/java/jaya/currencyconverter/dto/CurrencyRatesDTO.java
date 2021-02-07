package jaya.currencyconverter.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import lombok.Data;

@Data
public class CurrencyRatesDTO {

    private Map<String, BigDecimal> rates;
    private String base;
    private Date date;

    public CurrencyRatesDTO(){} 
}
