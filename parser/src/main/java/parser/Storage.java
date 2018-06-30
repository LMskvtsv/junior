package parser;

import java.util.LinkedList;

/**
 * This job can be used to parse Java jobs on the sql.ru web site.
 */
public class Storage {

    public final static String SQL_MAIN_PAGE = "http://www.sql.ru/forum/job-offers";
    private LinkedList<JobOffer> javaRaws = new LinkedList<>();
    private LinkedList<String> skippedPages = new LinkedList<>();

    public void addJavaRaws(JobOffer offer) {
        this.javaRaws.add(offer);
    }

    public void addSkippedRaws(String skippedPages) {
        this.skippedPages.add(skippedPages);
    }

    public LinkedList<String> getSkippedPages() {
        return skippedPages;
    }

    public LinkedList<JobOffer> getJavaRaws() {
        return javaRaws;
    }
}
