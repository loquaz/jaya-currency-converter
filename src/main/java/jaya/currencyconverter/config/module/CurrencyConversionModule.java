package jaya.currencyconverter.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jaya.currencyconverter.config.Router;
import jaya.currencyconverter.config.module.CurrencyConversionAnnotations.CurrencyConversionControllerLogger;
import jaya.currencyconverter.config.module.CurrencyConversionAnnotations.CurrencyConversionServiceLogger;
import jaya.currencyconverter.config.route.CurrencyConversionRouting;
import jaya.currencyconverter.controller.CurrencyConversionController;
import jaya.currencyconverter.repository.CurrencyConversionRepository;
import jaya.currencyconverter.service.CurrencyConversionService;
import jaya.currencyconverter.service.HttpClientServiceImpl;
import jaya.currencyconverter.service.IHttpClientService;

public class CurrencyConversionModule extends AbstractModule{

    @Provides
    @CurrencyConversionServiceLogger
    static Logger providesCurrencyConversionServiceLogger(){
        return LoggerFactory.getLogger(CurrencyConversionService.class);
    }

    @Provides
    @CurrencyConversionControllerLogger
    static Logger providesCurrencyConversionControllerLogger(){
        return LoggerFactory.getLogger(CurrencyConversionController.class);
    }

    @Override
    protected void configure(){
        bind(CurrencyConversionController.class);
        bind(CurrencyConversionRepository.class);
        bind(IHttpClientService.class).toInstance(new HttpClientServiceImpl());
        Multibinder.newSetBinder(binder(), Router.class).addBinding().to(CurrencyConversionRouting.class);
    }
    
}
