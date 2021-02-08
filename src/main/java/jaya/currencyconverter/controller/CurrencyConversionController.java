package jaya.currencyconverter.controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import io.javalin.http.Context;
import jaya.currencyconverter.config.module.CurrencyConversionAnnotations.CurrencyConversionControllerLogger;
import jaya.currencyconverter.dto.HttpResponseDTO;
import jaya.currencyconverter.dto.TransactionDTO;
import jaya.currencyconverter.entity.User;
import jaya.currencyconverter.service.CurrencyConversionService;

@Singleton
public class CurrencyConversionController {
    
    private CurrencyConversionService service;
    private String errorMessage;

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
            ctx.json( new HttpResponseDTO(201, "currency conversion result", false, result) );
		} catch (Exception e) {
            this.currencyConversionLogger.error(e.getMessage());
            ctx.status(400);
            ctx.json(new HttpResponseDTO(400, e.getMessage(),true, null));            
		}
    }

     /**
     * List all user transaction
     */
    public void listTransactionByUser(Context ctx)  {
 
        try {
            int userID = Integer.parseInt(ctx.queryParam("userID"));
            User user = this.service.getUserById(userID);
            
            if(user == null){
                errorMessage = "User with id " + userID + " not found";             
                currencyConversionLogger.error( errorMessage);
                throw new Exception( errorMessage );
            }

            List<TransactionDTO> transactions = this.service.getAllUserTransactions(userID);
            ctx.status(200);
            ctx.json( new HttpResponseDTO(200, "user transactions", false, transactions) );
		} catch (Exception e) {
            this.currencyConversionLogger.error(e.getMessage());
            ctx.status(400);
            ctx.json(new HttpResponseDTO(400, e.getMessage(),true, null));            
		}
    }

}
