package jaya.currencyconverter.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Data;

@Data
@DatabaseTable(tableName = "transactions")
public class CurrencyConversionTransaction extends Entity{
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(canBeNull = false, foreign = true)
    private User user;

    @DatabaseField
    private String currencyFrom;
    
    @DatabaseField
    private BigDecimal valueFrom;
    
    @DatabaseField
    private String currencyTo;
    
    @DatabaseField
    private BigDecimal valueTo;
    
    @DatabaseField
    private BigDecimal rate;
    
    @DatabaseField(format="yyyy-MM-dd HH:mm:ss.SSS", dataType = DataType.DATE_STRING)
    private Date createdAt;

    public CurrencyConversionTransaction(){}
    
}
