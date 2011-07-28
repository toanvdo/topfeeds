package au.edu.unsw.cse.topfeeds.jobs;

import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class UpdateSocialScoresSchedule {

	public UpdateSocialScoresSchedule() throws SchedulerException {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		JobDetail jd = JobBuilder.newJob(UpdateSocialScores.class)
				.withIdentity("updateSocialScores").build();

		Trigger st = TriggerBuilder.newTrigger()
				.withIdentity("mytrigger2", Scheduler.DEFAULT_GROUP)
				.startAt(new Date())
				.withSchedule(SimpleScheduleBuilder.repeatHourlyForever(1))
				.build();
		sched.scheduleJob(jd, st);
		sched.start();
	}
}
