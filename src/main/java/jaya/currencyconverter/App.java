package jaya.currencyconverter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import jaya.currencyconverter.config.module.ServerModule;

/**
 * 
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Injector injector = Guice.createInjector( ServerModule.create() );
        injector.getInstance(WebEntryPoint.class).boot();          

    }    
}
