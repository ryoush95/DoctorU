package gujc.dotter.model;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Notice {
    public String title;
    public String content;
    public Date timestamp;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date date) {
        this.timestamp = date;
    }




}
