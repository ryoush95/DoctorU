package gujc.dotter.model;

import com.google.firebase.Timestamp;

public class Notice {
    String title;
    String content;

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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    Timestamp timestamp;

    public Notice(String title, String content,Timestamp timestamp) {
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }


}
