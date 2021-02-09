package jaya.currencyconverter.util;

import org.slf4j.Logger;

public class Util {

    public static void logAndThrowException(Logger logger, String message) throws Exception{
        logger.error( "database error" );
        throw new Exception( message );
    }
    
}
