package jaya.currencyconverter.config.module;

import com.google.inject.AbstractModule;
import org.jetbrains.annotations.NotNull;

import io.javalin.Javalin;
import jaya.currencyconverter.WebEntryPoint;

public class ServerModule extends AbstractModule{
 
    private Javalin app;
    private static boolean testMode = false;
    
    private ServerModule(Javalin app){
        this.app = app;
    }

    @NotNull
    public static ServerModule create() {
        return new ServerModule(Javalin.create());
    }

    @NotNull
    public static ServerModule test() {
        testMode = true;
        return create();
    }

    @Override
    protected void configure(){
        bind(Javalin.class).toInstance(app);
        bind(WebEntryPoint.class);
        if(testMode)
            DataStorageModule.test();
        install( new DataStorageModule() );
        install( new UserModule() );
        install( new CurrencyConversionModule() );        
        
    }
}
