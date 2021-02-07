package jaya.currencyconverter.service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import jaya.currencyconverter.dto.CurrencyRatesDTO;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class HttpClientServiceImpl implements IHttpClientService {

	private final String baseUrl = "https://api.exchangeratesapi.io/";
	
	@Override
	public CurrencyRatesDTO getRates(String base, Date start, Date end)  {
		
		Retrofit retrofit = new Retrofit.Builder()
		.baseUrl(this.baseUrl)
		.addConverterFactory(JacksonConverterFactory.create())
		.build();

		ICurrencyRatesClient client = retrofit.create(ICurrencyRatesClient.class);

		CompletableFuture<CurrencyRatesDTO> response = client.getRates(base);

		try{
			CurrencyRatesDTO rates = response.get();
			return rates;
		}catch(InterruptedException e ){}
		catch(ExecutionException e){}		

		return null;
	}

	public HttpClientServiceImpl(){}
    
}
