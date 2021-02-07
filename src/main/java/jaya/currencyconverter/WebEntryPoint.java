package jaya.currencyconverter;

import java.util.Collections;
import java.util.Set;

import javax.inject.Singleton;

import com.google.inject.Inject;

import io.javalin.Javalin;
import jaya.currencyconverter.config.Router;

@Singleton
public class WebEntryPoint {
 
    private Javalin app;
    
    @Inject(optional = true)    
    private Set<Router> routes = Collections.emptySet();

    @Inject
    WebEntryPoint(Javalin app){
        this.app = app;
    }

    private void bindRoutes(){
        routes.forEach(route ->{
            route.route();
        });
    }
    
    public void boot(){
        bindRoutes();
        this.app.start(5000);
    }

}
