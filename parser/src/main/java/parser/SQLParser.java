package parser;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

/**
 * This job can be used to parse Java jobs on the sql.ru web site.
 */
public class SQLParser implements Job {

    private final static Logger LOG = Logger.getLogger(SQLParser.class);

    private Properties properties;
    private static File file;
    private final static String FIRST_LAUNCH_KEY = "Launch.firstLaunch";
    private Storage storage = new Storage();


    /**
     * Opens page, gets job offer table raws and adds them to collection as JobOffer objects. If
     * connection was not stable - page is added into skipped pages collection.
     *
     * @param page
     * @param filterDate
     * @return
     */
    private boolean filterPage(String page, Timestamp filterDate) {
        boolean needToGoFurther = true;
        Document doc;
        try {
            doc = Jsoup.connect(page).get();
        } catch (IOException e) {
            e.printStackTrace();
            storage.addSkippedRaws(page);
            LOG.warn(page + "was skipped.");
            return true;
        }

        Element forumTable = doc.select("table.forumTable").first();
        Elements raws = forumTable.select("tr");
        for (Element e: raws) {
            JobOffer offer = new JobOffer();
            boolean needToSave = false;
            Element topic = e.select("td.postslisttopic").first();
            if (topic != null) {
                Element href = topic.select("a").first();
                if (href != null) {
                    String s = href.text().toLowerCase();
                    if (s.contains("java") && !s.contains("script")) {
                        offer.setTitle(href.text());
                        offer.setHref(href.attr("href"));
                        needToSave = true;
                    }
                }
            }
            if (needToSave) {
                Element date = e.select("td:nth-child(6)").first();
                offer.setPostDate(date.text());
                if (offer.getPostDate().before(filterDate)) {
                    needToGoFurther = false;
                    LOG.info(String.format("Offer date %s before filter date %s. Stop loading vacancies.", offer.getPostDate(), filterDate));
                    break;
                }
                LOG.info(String.format("Found Java job! Title: %s. Saving...", offer.getTitle()));
                storage.addJavaRaws(offer);
            }
        }
        return needToGoFurther;
    }

    /**
     * Gets last page number.
     *
     * @return int
     */
    private int getLastPageNumber() {
        Document doc = null;
        try {
            doc = Jsoup.connect(Storage.SQL_MAIN_PAGE).timeout(60_000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element pages = doc.select("#content-wrapper-forum > table:nth-child(6) > tbody > tr > td:nth-child(1)").first();
        int lastPage = Integer.valueOf(pages.children().last().text());
        LOG.info(String.format("Last page number is %d", lastPage));
        return lastPage;
    }

    /**
     * Creates all paths for all pages numbers, because all pages are not availible from html document.
     *
     * @param lastPageNumber
     * @return list of all pages paths.
     */
    private LinkedList<String> getAllPages(int lastPageNumber) {
        LinkedList<String> list = new LinkedList<>();
        for (int i = 1; i <= lastPageNumber; i++) {
            list.add(Storage.SQL_MAIN_PAGE + "/" + i);
        }
        return list;
    }

    /**
     * Cut offer id from reference for each selected job offer and set as forum id.
     */
    private void setIDForOffers() {
        for (JobOffer o: storage.getJavaRaws()) {
            String href = o.getHref();
            String[] arr = href.split("/");
            o.setForumID(arr[4]);
        }
    }

    /**
     * Retuns first launch flag from properties.
     *
     * @return
     */
    private boolean isFirstLaunch() {
        return Boolean.valueOf(properties.getProperty("Launch.firstLaunch"));
    }

    /**
     * Get through all pages and filter them.
     *
     * @param filterDate
     * @param pages
     */
    private void action(Timestamp filterDate, LinkedList<String> pages) {
        for (String s: pages) {
            LOG.info(s);
            if (!this.filterPage(s, filterDate)) {
                break;
            }
        }
        this.setIDForOffers();
    }


    /**
     * Main execution of the job.
     *
     * @param jobExecutionContext used to get some parameters from scheduler.
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LOG.info("Parser has just started his dirty job.");
        try {
            properties = (Properties) jobExecutionContext.getScheduler().getContext().get("properties");
            file = (File) jobExecutionContext.getScheduler().getContext().get("file");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        if (properties == null) {
            LOG.error("Cannot resume execution, properties are null.");
            System.exit(0);
        }
        DataBaseObject dataBaseObject = new DataBaseObject(properties);
        Date date = new Date(System.currentTimeMillis());
        Timestamp t;
        if (isFirstLaunch()) {
            t = new Timestamp(DateUtils.truncate(date, Calendar.YEAR).getTime());
            LOG.info(String.format("First launch, loading all jobs since  %s", t));
            dataBaseObject.executeDBScripts("sql/parserDBInit.sql");
            action(t, getAllPages(getLastPageNumber()));
            markFirstLaunchAsFalse(file);
        } else {
            t = new Timestamp(DateUtils.truncate(date, Calendar.DATE).getTime());
            LOG.info(String.format("Regular launch, loading all jobs since today -  %s", t));
            action(t, getAllPages(getLastPageNumber()));
        }
        if (storage.getSkippedPages().size() > 0) {
            action(t, storage.getSkippedPages());
        }
        dataBaseObject.saveDataToDatabase(storage.getJavaRaws());
        LOG.info("See you soon space cowboy...");
    }

    /**
     * Mark first launch as 'false' and saves parameter back to file.
     *
     * @param file - original file that was supplied on the start of application.
     */
    private void markFirstLaunchAsFalse(File file) {
        properties.setProperty(FIRST_LAUNCH_KEY, String.valueOf(false));
        try (OutputStream out = new FileOutputStream(file)) {
            if (out != null) {
                properties.store(out, "Launch flag was changed to 'false'");
                LOG.info("Properties were changed.");
            } else {
                LOG.error("Cannot change properties.");
            }
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
