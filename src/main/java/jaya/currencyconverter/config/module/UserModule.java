package jaya.currencyconverter.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jaya.currencyconverter.config.Router;
import jaya.currencyconverter.config.module.UserServiceAnnotations.UserControllerLogger;
import jaya.currencyconverter.config.module.UserServiceAnnotations.UserServiceLogger;
import jaya.currencyconverter.config.route.UserRouting;
import jaya.currencyconverter.controller.UserController;
import jaya.currencyconverter.repository.UserRepository;
import jaya.currencyconverter.service.UserService;

public class UserModule extends AbstractModule{

    @Provides
    @UserServiceLogger
    static Logger providesUserServiceLogger(){
        return LoggerFactory.getLogger(UserService.class);
    }

    @Provides
    @UserControllerLogger
    static Logger providesUserControllerLogger(){
        return LoggerFactory.getLogger(UserController.class);
    }

    @Override
    protected void configure(){
        bind(UserController.class);
        bind(UserRepository.class);
        Multibinder.newSetBinder(binder(), Router.class).addBinding().to(UserRouting.class);
    }
    
}
