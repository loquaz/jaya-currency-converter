package jaya.currencyconverter.repository;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.j256.ormlite.dao.Dao;
import jaya.currencyconverter.config.module.DataStorageAnnotations.UserDao;
import jaya.currencyconverter.entity.User;

@Singleton
public class UserRepository {

	private Dao<User, Integer> dao;

	@Inject
	public UserRepository(@UserDao Dao<User, Integer> dao){
		this.dao = dao;
	}

	public User saveUser(User user) throws SQLException {
		return this.dao.createIfNotExists(user);
	}

	public User findUserById(int id) throws SQLException{
		return this.dao.queryForId(id);
	}
    
}
