package jaya.currencyconverter.config.route;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

import jaya.currencyconverter.config.Router;
import jaya.currencyconverter.controller.UserController;

@Singleton
public class UserRouting extends Router<UserController>{

    private Javalin app;

    @Inject
    public UserRouting(Javalin app){
        this.app = app;        
    }

	@Override
	public void route() {
		this.app.routes(()->{
            path("api/user/signup", () -> {
                post(ctx -> getController().signup(ctx));
            });
        });	
	}

}