package com.fanap.schedulerportal.portal.scheduler;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class QuartzTest {

    public static void main(String[] args) {
        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            setRepetitiveTask(HelloJob.class, scheduler, 4, 1571724869770L, 1571724879770L);


        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    public static void setRepetitiveTask(Class repetitiveTask, Scheduler scheduler, int hour, Long startDate, Long endDate) throws SchedulerException {
        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(repetitiveTask)
                .withIdentity("job1", "group1")
                .usingJobData("eddy","1")
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(new Date(startDate))

                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(hour)
                        .repeatForever())
                .endAt(new Date(endDate))
                .build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);
    }

}