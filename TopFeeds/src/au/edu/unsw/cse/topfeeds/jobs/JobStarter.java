package au.edu.unsw.cse.topfeeds.jobs;

import org.quartz.SchedulerException;

public class JobStarter {

	public static void main(String[] args) throws SchedulerException {
		new RetrieveUpdatePostSchedule();
//		new UpdateSocialScoresSchedule();
	}
}
