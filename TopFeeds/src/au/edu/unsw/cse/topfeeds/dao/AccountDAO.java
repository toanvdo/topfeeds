package au.edu.unsw.cse.topfeeds.dao;

import java.util.List;

import au.edu.unsw.cse.topfeeds.model.Account;
import au.edu.unsw.cse.topfeeds.model.TopFeedsUser;


public interface AccountDAO {
	
	void registerTopFeedsUser(TopFeedsUser topFeedsUser) throws Exception;
	void disableTopFeedsUser(TopFeedsUser topFeedsUser);
	void registerAccount(Account account) throws Exception;
	void updateAccount(Account account);
	List<Account> getAllActiveAccounts() throws Exception;
	int registerSocialNetworkUser(String unique) throws Exception;
	List<Account> getAccount(int userId) throws Exception;
	TopFeedsUser getTopfeedsUser(String username, String password) throws Exception;


}