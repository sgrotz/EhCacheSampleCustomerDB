package com.quartz;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

import net.sf.ehcache.CacheManager;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class runScheduler {

	
	public static CacheManager manager;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//manager = CacheManager.newInstance("config/ehcache.xml");

		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			
			// Create a new job data map and add the cachemanager into it. 
			// That way, the job can access the shared object. 
			// JobDataMap jdm = new JobDataMap();
			// jdm.put("cacheManager", manager);
			
			scheduler.start();
			
			
			// Create a new job ...
			JobDetail job = newJob(SyncCustomersDBwithCache.class)
			        .withIdentity("LoadCustomersIntoDB", "Static Data")
			        //.usingJobData(jdm)
			        .build();
			
			// Create a new trigger ...
		    Trigger trigger = newTrigger()
		            .withIdentity("TriggerCustomerLoad", "Static Data")
		            // .startNow()
		            .withSchedule(simpleSchedule()
		            		// Set the synchronization interval here: Default 30
		                    .withIntervalInSeconds(5)
		                    .repeatForever()
		                    )            
		            .build();

		    // Make sure to tell the scheduler about the new job...
		    scheduler.scheduleJob(job, trigger);
			
			
			System.out.println("Scheduler is now running ... ");
			
			while (true) {
				// Infinite loop to make sure the scheduler keeps running ;) ...
				Thread.sleep(1000);
			}	

			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
