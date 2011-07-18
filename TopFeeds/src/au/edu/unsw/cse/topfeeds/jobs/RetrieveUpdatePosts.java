package au.edu.unsw.cse.topfeeds.jobs;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

import au.edu.unsw.cse.topfeeds.dao.AccountDAO;
import au.edu.unsw.cse.topfeeds.dao.impl.AccountDAOImpl;

public class RetrieveUpdatePosts implements Job{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AccountDAO acctDAO = new AccountDAOImpl();
		acctDAO.getAccount("test");
		
		// TODO Auto-generated method stub
//		  SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
//
//		  Scheduler sched = schedFact.getScheduler();
//
//		  sched.start();
//
//		  // define the job and tie it to our HelloJob class
//		  JobDetail job = new Job(RetrieveUpdatePosts.class)
//		      .withIdentity("myJob", "group1")
//		      .build();
//		        
//		  // Trigger the job to run now, and then every 40 seconds
//		  Trigger trigger = new Trigger()
//		      .withIdentity("myTrigger", "group1")
//		      .startNow()
//		      .withSchedule(simpleSchedule()
//		          .withIntervalInSeconds(40)
//		          .repeatForever())            
//		      .build();
//		        
//		  // Tell quartz to schedule the job using our trigger
//		  sched.scheduleJob(job, trigger);
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		//retrieve accounts records which are active in status then call appropriate external 
		
		
		
	}

}
