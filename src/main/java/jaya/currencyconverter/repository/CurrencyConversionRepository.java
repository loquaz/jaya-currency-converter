package jaya.currencyconverter.repository;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.j256.ormlite.dao.Dao;

import jaya.currencyconverter.config.module.DataStorageAnnotations.CurrencyConversionDao;
import jaya.currencyconverter.entity.CurrencyConversionTransaction;

@Singleton
public class CurrencyConversionRepository {

	private Dao<CurrencyConversionTransaction, Integer> dao;

	@Inject
	public CurrencyConversionRepository(@CurrencyConversionDao Dao<CurrencyConversionTransaction, Integer> dao){
		this.dao = dao;
	}

	public CurrencyConversionRepository(){}

	public CurrencyConversionTransaction saveTransaction(CurrencyConversionTransaction transaction) throws SQLException {
		return this.dao.createIfNotExists(transaction);
	}

	public List<CurrencyConversionTransaction> findTransactionsByUserId(int userID) throws SQLException {
		return this.dao.queryForEq("user_id", userID);
	}
    
}
