package parser;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class JobOffer {

    private String forumID;
    private String title;
    private String href;
    private Timestamp postDate;

    public String getForumID() {
        return forumID;
    }

    public void setForumID(String forumID) {
        this.forumID = forumID;
    }

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setPostDate(String postDate) {
        try {
            this.postDate = transformDate(postDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Transforms forum date into TimeStamp object.
     *
     * @param dateTime
     * @return
     * @throws ParseException
     */
    private Timestamp transformDate(String dateTime) throws ParseException {
        Timestamp timestamp;
        String[] arr = dateTime.split(",");
        String dateString = arr[0];
        String timeString = arr[1];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date theDate;
        if (dateString.toLowerCase().contains("сегодня")) {
            theDate = cal.getTime();
        } else if (dateString.toLowerCase().contains("вчера")) {
            cal.add(Calendar.DATE, -1);
            theDate = cal.getTime();
        } else {
            theDate = new SimpleDateFormat("dd MMM yy", new Locale("ru")).parse(dateString);
        }
        String formattedDateString = dateFormat.format(theDate);
        Date finalDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(formattedDateString + timeString);
        timestamp = new Timestamp(finalDate.getTime());
        return timestamp;
    }
}
