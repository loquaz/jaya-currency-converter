package jaya.currencyconverter.config.route;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.get;

import jaya.currencyconverter.config.Router;
import jaya.currencyconverter.controller.CurrencyConversionController;

@Singleton
public class CurrencyConversionRouting extends Router<CurrencyConversionController>{

    private Javalin app;

    @Inject
    public CurrencyConversionRouting(Javalin app){
        this.app = app;        
    }

	@Override
	public void route() {
		this.app.routes(()->{
            path("api/convert", () -> {
                post(ctx -> getController().doConversion(ctx));
            });
            path("api/transactions-by-user", () -> {
                get(ctx -> getController().listTransactionByUser(ctx));
            });
            
        });	
	}

}