package jaya.currencyconverter.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import jaya.currencyconverter.config.module.CurrencyConversionAnnotations.CurrencyConversionServiceLogger;
import jaya.currencyconverter.dto.CurrencyRates;
import jaya.currencyconverter.dto.TransactionDTO;
import jaya.currencyconverter.entity.CurrencyConversionTransaction;
import jaya.currencyconverter.entity.User;
import jaya.currencyconverter.repository.CurrencyConversionRepository;
import jaya.currencyconverter.repository.UserRepository;

@Singleton
public class CurrencyConversionService {

    private CurrencyConversionRepository repository;

    private UserRepository userRepository;

    private IHttpClientService httpClientService;

    @Inject
    @CurrencyConversionServiceLogger
    private Logger serviceLogger;

    @Inject
    public CurrencyConversionService(CurrencyConversionRepository repository,
                                     UserRepository userRepository,
                                     IHttpClientService httpClientService){
        this.repository         = repository;
        this.userRepository     = userRepository;
        this.httpClientService  = httpClientService;
    }

    public TransactionDTO createTransaction(TransactionDTO transaction) throws Exception {
        try {
            String currencyFrom         = transaction.getCurrencyFrom();
            String currencyTo           = transaction.getCurrencyTo();
            BigDecimal amount           = transaction.getAmount();
            int userID                  = transaction.getUserID();    

            if(currencyFrom == null || currencyFrom.isEmpty()){
                serviceLogger.error("currencyFrom can't be empty");
                throw new Exception("currencyFrom can't be empty"); 
            }

            if(currencyTo == null || currencyTo.isEmpty()){
                serviceLogger.error("currencyTo can't be empty");
                throw new Exception("currencyTo can't be empty"); 
            }

            if(amount == null || ( amount.compareTo(BigDecimal.ZERO) == 0 ) ){
                serviceLogger.error("amount can't be null or equals to 0");
                throw new Exception("amount can't be null or equals to 0"); 
            }

            if(userID == 0){
                serviceLogger.error("userID can't be null");
                throw new Exception("userID can't be null"); 
            }

            CurrencyRates rates = this.httpClientService.getRates( currencyFrom, null, null );
            User user = this.userRepository.findUserById( userID );
            
            BigDecimal rate = rates.getRates().get( currencyTo );
            
            BigDecimal valueConverted = convert( rate, amount );            
            
            CurrencyConversionTransaction conversionTransaction = new CurrencyConversionTransaction();
            
            conversionTransaction.setUser(user);
            conversionTransaction.setValueTo(valueConverted);
            conversionTransaction.setCurrencyFrom(currencyFrom);        
            conversionTransaction.setCurrencyTo(currencyTo);
            conversionTransaction.setValueFrom(amount);
            conversionTransaction.setRate(rate);
            conversionTransaction.setCreatedAt(new Date());
            
            this.repository.saveTransaction(conversionTransaction);
            return new TransactionDTO( conversionTransaction );

		} catch (SQLException e) {
			serviceLogger.error(e.getCause().getMessage());
            return null;
		}catch(Exception e){
            serviceLogger.error(e.getMessage());
            return null;
        }
    }
    
    private BigDecimal convert(BigDecimal to, BigDecimal amount){
        return to.multiply(amount);
    }
    
}
