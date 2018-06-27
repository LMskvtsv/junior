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

    public static void main(String[] args) {
        file = new File(args[0]);
        loadProperties(file);
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

        Scheduler sched;
        try {
            sched = schedFact.getScheduler();
            sched.getContext().put("properties", properties);
            sched.getContext().put("file", file);

            sched.start();

            JobDetail jobDetail = newJob(SQLParser.class)
                    .withIdentity("SQLParser", "group") // name "myJob", group "group1"
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
