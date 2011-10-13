package au.edu.unsw.cse.topfeeds.jobs;

import au.edu.unsw.cse.topfeeds.model.Account;

public class InitialSetup extends Thread {

	Account account;

	public InitialSetup(Account account) {
		this.account = account;
	}

	@Override
	public void run() {
		new UpdateSocialScores().updateSocialScoreForAccount(account);
		new RetrieveUpdatePosts().updatePostFromCurrentUser(account);
	}

}
