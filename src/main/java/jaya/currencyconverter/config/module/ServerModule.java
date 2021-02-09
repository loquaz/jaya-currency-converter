package jaya.currencyconverter.config.module;

import com.google.inject.AbstractModule;
import org.jetbrains.annotations.NotNull;

import io.javalin.Javalin;
import jaya.currencyconverter.WebEntryPoint;

import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;

public class ServerModule extends AbstractModule{
 
    private Javalin app;
    private static boolean testMode = false;
    
    private ServerModule(Javalin app){
        this.app = app;
    }

    @NotNull
    public static ServerModule create() {
        return new ServerModule(Javalin.create( conf -> {
            conf.registerPlugin( new OpenApiPlugin( getOpenApiOptions() ) );
        } ));
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

    private static OpenApiOptions getOpenApiOptions(){
        Info info = new Info()
        .version("1.0")
        .description("Jaya Currency Conversion");
        
        return new OpenApiOptions(info).path("/swagger-docs")
        .activateAnnotationScanningFor("jaya.currencyconverter")
        .swagger( new SwaggerOptions("/swagger-ui") )
        .reDoc( new ReDocOptions("/redoc") );
    }
}
