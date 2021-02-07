package jaya.currencyconverter.config.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Data Storage annotations
 */
public class CurrencyConversionAnnotations {
    
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CurrencyConversionServiceLogger {}
    
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
	public @interface CurrencyConversionControllerLogger {}
}