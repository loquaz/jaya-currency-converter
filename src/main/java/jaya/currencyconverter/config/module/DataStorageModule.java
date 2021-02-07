package jaya.currencyconverter.config.module;

import java.io.File;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jaya.currencyconverter.config.module.DataStorageAnnotations.CurrencyConversionDao;
import jaya.currencyconverter.config.module.DataStorageAnnotations.UserDao;
import jaya.currencyconverter.entity.CurrencyConversionTransaction;
import jaya.currencyconverter.entity.User;

public class DataStorageModule extends AbstractModule {

    private String connectionString = "jdbc:sqlite:" + dbPath();
    private static ConnectionSource connectionSource;
    private static boolean test = false;
    private Logger logger = LoggerFactory.getLogger(DataStorageModule.class);
    
    @Provides
    @UserDao
    static Dao<User, Integer> providesUserDao() throws SQLException{
        return DaoManager.createDao(connectionSource, User.class); //userDao;
    }

    @Provides
    @CurrencyConversionDao
    static Dao<CurrencyConversionTransaction, Integer> providesCurrencyConversionDao() throws SQLException{
        return DaoManager.createDao(connectionSource, CurrencyConversionTransaction.class); 
    }

    private String dbPath(){
        
        return test ? System.getProperty("user.dir") + "/db/test_currency_converter.db"
        : System.getProperty("user.dir") + "/db/currency_converter.db";
    }

    private String dbDir(){
        
        return System.getProperty("user.dir") + "/db";
    }

    @Override
    protected void configure(){

        File dbDirectory = new File(dbDir());
        if(!dbDirectory.exists()) dbDirectory.mkdir();

        if(test) new File(dbPath()).delete();
        
        Set<Class> entities = new HashSet();
        
        entities.add(User.class);
        entities.add(CurrencyConversionTransaction.class);
        
        try {
            connectionSource = new JdbcConnectionSource(connectionString);
        } catch (SQLException e) {
            logger.error("Error connecting to database");            
        }    
    
        entities.forEach(entity ->{
            try {
                
                logger.info("Trying to create table for entity: " + entity.getSimpleName());
                TableUtils.createTable(connectionSource, entity);
                
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        });        
        
    } 
    
    public static void test(){
        test = true;
    }
    
}
