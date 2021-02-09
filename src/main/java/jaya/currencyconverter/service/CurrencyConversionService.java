package jaya.currencyconverter.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

import jaya.currencyconverter.config.module.CurrencyConversionAnnotations.CurrencyConversionServiceLogger;
import jaya.currencyconverter.dto.CurrencyRatesDTO;
import jaya.currencyconverter.dto.TransactionDTO;
import jaya.currencyconverter.entity.CurrencyConversionTransaction;
import jaya.currencyconverter.entity.User;
import jaya.currencyconverter.repository.CurrencyConversionRepository;
import jaya.currencyconverter.repository.UserRepository;
import jaya.currencyconverter.util.Util;

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
            
            if(currencyFrom == null || currencyFrom.isEmpty())
                Util.logAndThrowException(serviceLogger, "currencyFrom can't be empty");
            
            if(currencyTo == null || currencyTo.isEmpty())
                Util.logAndThrowException(serviceLogger, "currencyTo can't be empty");

            if(amount == null || ( amount.compareTo(BigDecimal.ZERO) == 0 ) )
                Util.logAndThrowException(serviceLogger, "amount can't be null or equals to 0");

            if(userID == 0)
                Util.logAndThrowException(serviceLogger, "userID can't be null");

            CurrencyRatesDTO rates = this.httpClientService.getRates( currencyFrom, null, null );

            if(rates == null)
                Util.logAndThrowException(serviceLogger, "error querying rates for [ " + currencyFrom + " ] ");

            User user = this.userRepository.findUserById( userID );
            
            BigDecimal rate = rates.getRates().get( currencyTo );

            if(rate == null)
                Util.logAndThrowException(serviceLogger, "currency not found [ " + currencyTo + " ] ");
            
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
			String msg = e.getCause() != null ? e.getCause().getMessage()
                                              : e.getMessage();

            Util.logAndThrowException(serviceLogger, "database error " + msg );

		}catch(Exception e){

            String msg = e.getCause() != null ? e.getCause().getMessage()
                                              : e.getMessage(); 
                                              
            Util.logAndThrowException(serviceLogger, "transaction error " + msg );
            
        }

        return null;
        
    }
    
    private BigDecimal convert(BigDecimal to, BigDecimal amount){
        return to.multiply(amount);
    }

    public List<TransactionDTO> getAllUserTransactions(int userID) throws SQLException {
        List<CurrencyConversionTransaction> transactions = this.repository.findTransactionsByUserId(userID);
        return transactions.stream()
                           .map(TransactionDTO::new)
                           .collect(Collectors.toList());         
    }

    public User getUserById(int userID) throws SQLException {
        return this.userRepository.findUserById(userID);
    }
    
}
