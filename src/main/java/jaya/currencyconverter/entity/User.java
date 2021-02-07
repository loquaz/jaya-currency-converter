package jaya.currencyconverter.entity;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Data;

@DatabaseTable(tableName = "users")
@Data
public class User extends Entity{
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(unique = true)
    private String username;
    @DatabaseField
    private String password;

    @ForeignCollectionField(eager = false)
    Collection<CurrencyConversionTransaction> transactions;

    public User(){}

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.transactions = null;
    }

    public User(    String username, 
                    String password, 
                    ForeignCollection<CurrencyConversionTransaction> transactions){
        this.username       = username;
        this.password       = password;
        this.transactions   = transactions;
    }
}
