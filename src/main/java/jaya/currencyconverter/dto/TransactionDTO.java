package jaya.currencyconverter.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import jaya.currencyconverter.entity.CurrencyConversionTransaction;
import lombok.Data;

@Data
public class TransactionDTO {
    private int userID;
    private String currencyFrom;
    private BigDecimal amount;
    private String currencyTo;
    private BigDecimal valueTo;
    private BigDecimal conversionRate;
    private String date;
    
    public TransactionDTO(){}

    public TransactionDTO(CurrencyConversionTransaction transaction){
        this.userID         = transaction.getUser().getId();
        this.amount         = transaction.getValueFrom();
        this.currencyFrom   = transaction.getCurrencyFrom();
        this.currencyTo     = transaction.getCurrencyTo();
        this.conversionRate = transaction.getRate();
        this.valueTo        = transaction.getValueTo();
        this.date           = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(transaction.getCreatedAt());
    }
}
