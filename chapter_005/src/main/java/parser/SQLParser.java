package parser;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

public class SQLParser {

    private final static Logger LOG = Logger.getLogger(SQLParser.class);

    private Properties properties = new Properties();
    private final static String CONFIG_PATH = "config/ParserDBConfig.cfg";
    private final static String FIRST_LAUNCH_KEY = "Launch.firstLaunch";
    private final static String DB_PATH_KEY = "Database.URL";
    private final static String LOGIN_KEY = "Database.user";
    private final static String PASSWORD_KEY = "Database.password";
    private String sqlMainPage = "http://www.sql.ru/forum/job-offers";
    private LinkedList<JobOffer> javaRaws = new LinkedList<>();
    private LinkedList<String> skippedRaws = new LinkedList<>();


    private boolean filterPage(String page, Timestamp filterDate) {
        boolean needToGoFurther = true;
        Document doc;
        try {
            doc = Jsoup.connect(page).get();
        } catch (IOException e) {
            e.printStackTrace();
            skippedRaws.add(page);
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
                javaRaws.add(offer);
            }
        }
        return needToGoFurther;
    }

    private int getLastPageNumber() {
        Document doc = null;
        try {
            doc = Jsoup.connect(sqlMainPage).timeout(60_000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element pages = doc.select("#content-wrapper-forum > table:nth-child(6) > tbody > tr > td:nth-child(1)").first();
        int lastPage = Integer.valueOf(pages.children().last().text());
        LOG.info(String.format("Last page number is %d", lastPage));
        return lastPage;
    }

    private LinkedList<String> getAllPages(int lastPageNumber) {
        LinkedList<String> list = new LinkedList<>();
        for (int i = 1; i <= lastPageNumber; i++) {
            list.add(sqlMainPage + "/" + i);
        }
        return list;
    }

    private void setIDForOffers() {
        for (JobOffer o: javaRaws) {
            String href = o.getHref();
            String[] arr = href.split("/");
            o.setForumID(arr[4]);
        }
    }

    private boolean isFirstLaunch() {
        return Boolean.valueOf(properties.getProperty("Launch.firstLaunch"));
    }

    private void markFirstLaunchAsFalse() {
        properties.setProperty(FIRST_LAUNCH_KEY, String.valueOf(false));
        try (OutputStream out = new FileOutputStream(this.getClass().getClassLoader().getResource(CONFIG_PATH).getFile())) {
            properties.store(out, "Launch flag was changed to 'false'");
            LOG.info("Properties were changed.");
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void loadProperties() {
        try (InputStream fis = new FileInputStream(this.getClass().getClassLoader().getResource(CONFIG_PATH).getFile())) {
            if (fis != null) {
                properties.load(fis);
            } else {
                LOG.error("Cannot load properties.");
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void action(Timestamp filterDate, LinkedList<String> pages) {
        for (String s: pages) {
            LOG.info(s);
            if (!this.filterPage(s, filterDate)) {
                break;
            }
        }
        this.setIDForOffers();
    }

    private void saveDataToDatabase(LinkedList<JobOffer> list) {
        Connection connection = null;
        try (Connection conn = DriverManager.getConnection(String.valueOf(properties.get(DB_PATH_KEY)),
                String.valueOf(properties.get(LOGIN_KEY)),
                String.valueOf(properties.get(PASSWORD_KEY)))) {
            if (conn != null) {
                connection = conn;
                conn.setAutoCommit(false);
                PreparedStatement ps;
                for (JobOffer offer: list) {
                    ps = conn.prepareStatement("INSERT INTO job_offers (forum_id, title, href, created) VALUES (?, ?, ?, ?) ON CONFLICT (forum_id) DO NOTHING");
                    ps.setString(1, offer.getForumID());
                    ps.setString(2, offer.getTitle());
                    ps.setString(3, offer.getHref());
                    ps.setTimestamp(4, offer.getPostDate());
                    LOG.info(ps.toString());
                    ps.executeUpdate();
                }
                conn.commit();
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    LOG.error(e1.getMessage(), e1);
                }
            }
        }
    }


    private boolean executeDBScripts(String fileName) {
        try (Connection conn = DriverManager.getConnection(String.valueOf(properties.get(DB_PATH_KEY)),
                String.valueOf(properties.get(LOGIN_KEY)),
                String.valueOf(properties.get(PASSWORD_KEY)))) {
            BufferedReader in = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)));
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = in.readLine()) != null) {
                sb.append(str + System.lineSeparator());
            }
            in.close();
            LOG.info(sb.toString());
            conn.createStatement().executeUpdate(sb.toString());
        } catch (Exception e) {
            LOG.error(String.format("Failed to execute %s.", fileName));
            LOG.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        LOG.info("Parser has just started his dirty job.");
        SQLParser parser = new SQLParser();
        parser.loadProperties();
        Date date = new Date(System.currentTimeMillis());
        Timestamp t;
        if (parser.isFirstLaunch()) {
            t = new Timestamp(DateUtils.truncate(date, Calendar.YEAR).getTime());
            LOG.info(String.format("First launch, loading all jobs since  %s", t));
            parser.executeDBScripts("sql/parserDBInit.sql");
            parser.action(t, parser.getAllPages(parser.getLastPageNumber()));
            parser.markFirstLaunchAsFalse();
        } else {
            t = new Timestamp(DateUtils.truncate(date, Calendar.DATE).getTime());
            LOG.info(String.format("Regular launch, loading all jobs since today -  %s", t));
            parser.action(t, parser.getAllPages(parser.getLastPageNumber()));
        }
        if (parser.skippedRaws.size() > 0) {
            parser.action(t, parser.skippedRaws);
        }
        parser.saveDataToDatabase(parser.javaRaws);
    }
}
