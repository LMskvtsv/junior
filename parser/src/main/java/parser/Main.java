package parser;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class Main {

    private static Properties properties = new Properties();
    private static File file;
    private final static Logger LOG = Logger.getLogger(Main.class);
    private final static String CRON_KEY = "Cron.time";

    /**
     * Scheduler for SQLParser job. Schedule must be provided into config file under Cron.time key.
     *
     * @param args config file should be the first. Other parameters will be ignored.
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            file = new File(args[0]);
        } else {
            LOG.error("Cannot work without config file.");
            System.exit(0);
        }
        loadProperties(file);
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

        Scheduler sched;
        try {
            sched = schedFact.getScheduler();
            sched.getContext().put("properties", properties);
            sched.getContext().put("file", file);

            sched.start();

            JobDetail jobDetail = newJob(SQLParser.class)
                    .withIdentity("SQLParser", "group")
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity("sqlRuParser", "group")
                    .withSchedule(cronSchedule(properties.getProperty(CRON_KEY)))
                    .forJob("SQLParser", "group")
                    .build();

            sched.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads properties from file given when app launched.
     *
     * @param path
     */
    private static void loadProperties(File path) {
        try (InputStream fis = new FileInputStream(path)) {
            if (fis != null) {
                properties.load(fis);
            } else {
                LOG.error("Cannot load properties.");
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
