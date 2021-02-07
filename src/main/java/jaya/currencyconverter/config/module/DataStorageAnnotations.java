package jaya.currencyconverter.config.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Data Storage annotations
 */
public class DataStorageAnnotations {
    
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface UserDao {}
    
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CurrencyConversionDao {}
    
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CurrencyConversionRepo {}

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface UserRepo {}
}