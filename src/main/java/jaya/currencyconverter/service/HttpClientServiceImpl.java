package jaya.currencyconverter.service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import jaya.currencyconverter.dto.CurrencyRates;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class HttpClientServiceImpl implements IHttpClientService {

	private final String baseUrl = "https://api.exchangeratesapi.io/";
	
	@Override
	public CurrencyRates getRates(String base, Date start, Date end)  {
		
		Retrofit retrofit = new Retrofit.Builder()
		.baseUrl(this.baseUrl)
		.addConverterFactory(JacksonConverterFactory.create())
		.build();

		ICurrencyRatesClient client = retrofit.create(ICurrencyRatesClient.class);

		CompletableFuture<CurrencyRates> response = client.getRates(base);

		try{
			CurrencyRates rates = response.get();
			return rates;
		}catch(InterruptedException e ){}
		catch(ExecutionException e){}		

		return null;
	}

	public HttpClientServiceImpl(){}
    
}
