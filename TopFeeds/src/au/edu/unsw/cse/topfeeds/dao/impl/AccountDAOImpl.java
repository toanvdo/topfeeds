package au.edu.unsw.cse.topfeeds.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.cse.topfeeds.dao.AccountDAO;
import au.edu.unsw.cse.topfeeds.dao.DatabaseConnection;
import au.edu.unsw.cse.topfeeds.dao.SocialNetwork;
import au.edu.unsw.cse.topfeeds.model.Account;
import au.edu.unsw.cse.topfeeds.model.TopFeedsUser;

public class AccountDAOImpl implements AccountDAO {

	private Connection conn = DatabaseConnection.getConnection();

	public void registerAccount(Account account) throws Exception {
		try {
			PreparedStatement ps = conn
					.prepareStatement("INSERT ACCOUNT(userId, username,accessToken,type) VALUES (?,?,?,?)");
			ps.setInt(1, account.getUserId());
			ps.setString(2, account.getUsername());
			ps.setString(3, account.getAccessToken());
			ps.setString(4, account.getType().toString());

			int status = ps.executeUpdate();

			if (status == 0) {
				throw new Exception("Failed to insert Account, try again");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Account> getAllActiveAccounts() throws Exception {
		List<Account> accounts = null;
		try {
			PreparedStatement ps = conn
					.prepareStatement("SELECT A.id, A.userId, A.username,A.accessToken,A.type FROM ACCOUNT A, "
							+ "TOPFEEDS_USER TFU WHERE A.userId=TFU.id AND TFU.status='ACTIVE' "
							+ "ORDER BY A.userId ASC");

			ResultSet rs = ps.executeQuery();
			accounts = new ArrayList<Account>();

			while (rs.next()) {
				Account acct = new Account();
				acct.setId(rs.getInt(1));
				acct.setUserId(rs.getInt(2));
				acct.setUsername(rs.getString(3));
				acct.setAccessToken(rs.getString(4));
				acct.setType(SocialNetwork.valueOf(rs.getString(5)
						.toUpperCase()));

				accounts.add(acct);
			}
		} catch (SQLException e) {
			throw new Exception("Failed to get Active Accounts",e);
		}

		return accounts;

	}

	public void registerTopFeedsUser(TopFeedsUser topFeedsUser)
			throws Exception {

		try {
			int socialUserId = registerSocialNetworkUser(topFeedsUser
					.getEmail());

			topFeedsUser.setId(socialUserId);
		} catch (Exception e) {
			throw new Exception(
					"Failed to insert TopFeedsUser Stage1, try again");
		}

		try {
			PreparedStatement ps = conn
					.prepareStatement("INSERT TOPFEEDS_USER(id, username,password,emailAddress,mac) VALUES (?,?,?,?,?)");

			// insert after
			ps.setInt(1, topFeedsUser.getId());
			ps.setString(2, topFeedsUser.getUsername());
			ps.setString(3, topFeedsUser.getPassword());
			ps.setString(4, topFeedsUser.getEmail());
			ps.setString(5, topFeedsUser.getMac());

			int status = ps.executeUpdate();

			if (status == 0) {
				throw new Exception(
						"Failed to insert TopFeedsUser Stage2, try again");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void disableTopFeedsUser(TopFeedsUser topFeedsUser) {
		// TODO Auto-generated method stub

	}

	public void updateAccount(Account account) {
		// TODO Auto-generated method stub

	}

	public TopFeedsUser getTopfeedsUser(String username, String password)
			throws Exception {
		TopFeedsUser tfu = null;

		try {
			PreparedStatement ps = conn
					.prepareStatement("SELECT id, username, password,emailAddress,mac,status FROM TOPFEEDS_USER WHERE username=? AND password = ?");
			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			if (rs.first()) {
				tfu = new TopFeedsUser();
				tfu.setId(rs.getInt(1));
				tfu.setUsername(rs.getString(2));
				tfu.setPassword(rs.getString(3));
				tfu.setEmail(rs.getString(4));
				tfu.setMac(rs.getString(5));
				tfu.setStatus(rs.getString(6));
			}
		} catch (SQLException e) {
			throw new Exception("Failed to get Accounts",e);
		}

		return tfu;
	}

	public List<Account> getAccount(int userId) throws Exception {
		List<Account> accounts = null;
		try {
			PreparedStatement ps = conn
					.prepareStatement("SELECT id, userId, username,accessToken,type FROM ACCOUNT WHERE userId=?");
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();
			accounts = new ArrayList<Account>();

			while (rs.next()) {
				Account acct = new Account();
				acct.setId(rs.getInt(1));
				acct.setUserId(userId);
				acct.setUsername(rs.getString(3));
				acct.setAccessToken(rs.getString(4));
				acct.setType(SocialNetwork.valueOf(rs.getString(5)
						.toUpperCase()));

				accounts.add(acct);
			}
		} catch (SQLException e) {
			throw new Exception("Failed to get Accounts",e);
		}

		return accounts;
	}

	public int registerSocialNetworkUser(String realName) throws Exception {
		int userId = -1;
		try {
			PreparedStatement ps = conn
					.prepareStatement("INSERT SOCIAL_NETWORK_USER(realName) VALUES (?)");

			ps.setString(1, realName);
			int status = ps.executeUpdate();

			if (status == 0) {
				throw new Exception("Failed to insert Account, try again");
			}

			// fetch the ID;

			ps = conn.prepareStatement("SELECT LAST_INSERT_ID()");
			ResultSet rs = ps.executeQuery();
			if (rs.first()) {
				userId = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new Exception(
					"Failed to insert social network user, try again later",e);
		}
		
		return userId;
	}

	@Override
	public int getSocialNetworkUser(String identifier,
			SocialNetwork socialNetwork) throws Exception {
		int userId = -1;
		try {
			PreparedStatement ps = conn
					.prepareStatement("SELECT userId FROM ACCOUNT WHERE username=? AND type =?");
			ps.setString(1, identifier);
			ps.setString(2, socialNetwork.toString());

			ResultSet rs = ps.executeQuery();
			if (rs.first()) {
				userId = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new Exception("Failed to get Accounts",e);
		}

		return userId;
	}

	public int registerFriend(String identifer, SocialNetwork socialNetwork,
			String realName) {
		int userId = -1;
		
		try {
			userId = registerSocialNetworkUser(realName);
			Account acct = new Account();
			acct.setAccessToken("EMPTY");
			acct.setUserId(userId);
			acct.setType(socialNetwork);
			acct.setUsername(identifer);

			registerAccount(acct);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userId;

	}

}
