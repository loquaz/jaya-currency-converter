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
            String errorMsg;    

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

            CurrencyRatesDTO rates = this.httpClientService.getRates( currencyFrom, null, null );

            if(rates == null){
                errorMsg = "error querying rates for [ " + currencyFrom + " ] ";     
                serviceLogger.error( errorMsg );
                throw new Exception( errorMsg ); 
            }

            User user = this.userRepository.findUserById( userID );
            
            BigDecimal rate = rates.getRates().get( currencyTo );

            if(rate == null){
                errorMsg = "currency not found [ " + currencyTo + " ] ";     
                serviceLogger.error( errorMsg );
                throw new Exception( errorMsg ); 
            }
            
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
            
            serviceLogger.error( "database error" );
            serviceLogger.error( msg );
            throw new Exception( msg );

		}catch(Exception e){

            String msg = e.getCause() != null ? e.getCause().getMessage()
                                              : e.getMessage();  
            
            serviceLogger.error( "transaction error" );
            serviceLogger.error( msg );
            throw new Exception( msg );
            
        }
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
