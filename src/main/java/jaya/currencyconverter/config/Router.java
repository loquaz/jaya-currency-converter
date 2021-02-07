package jaya.currencyconverter.config;

import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;

import com.google.inject.Injector;

public abstract class Router<T> {
    
    @Inject
    private Injector injector;
    
    private Class<T> controller;
    
    public abstract void route();

    public T getController() {
        return injector.getInstance(getControllerFromGenericType());
    }

    private Class<T> getControllerFromGenericType() {
        if (controller == null) {
            controller = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return controller;
    }
}
