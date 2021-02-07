package jaya.currencyconverter.config.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Data Storage annotations
 */
public class UserServiceAnnotations {
    
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface UserServiceLogger {}

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface UserControllerLogger {}

}