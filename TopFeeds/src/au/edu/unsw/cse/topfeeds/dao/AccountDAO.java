package au.edu.unsw.cse.topfeeds.dao;

import au.edu.unsw.cse.topfeeds.model.Account;


public interface AccountDAO {
	
	void registerUser();
	void registerAccount(Account account);
	void updateAccount();

}
