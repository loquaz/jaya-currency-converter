package jaya.currencyconverter.controller;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import io.javalin.http.Context;
import jaya.currencyconverter.config.module.CurrencyConversionAnnotations.CurrencyConversionControllerLogger;
import jaya.currencyconverter.dto.HttpResponseDTO;
import jaya.currencyconverter.dto.TransactionDTO;
import jaya.currencyconverter.service.CurrencyConversionService;

@Singleton
public class CurrencyConversionController {
    
    private CurrencyConversionService service;

    @Inject
    @CurrencyConversionControllerLogger
    private Logger currencyConversionLogger;

    @Inject
    public CurrencyConversionController(CurrencyConversionService service){
        this.service = service;
    }

    /**
     * Do the currency convertion
     */
    public void doConversion(Context ctx)  {
 
        try {
            TransactionDTO transaction  = ctx.bodyAsClass(TransactionDTO.class);        
            TransactionDTO result       = this.service.createTransaction(transaction);
            ctx.status(201);
            ctx.json(result);
		} catch (Exception e) {
            this.currencyConversionLogger.error(e.getMessage());
            ctx.status(400);
            ctx.json(new HttpResponseDTO(400, e.getMessage(),true));            
		}
    }

}
