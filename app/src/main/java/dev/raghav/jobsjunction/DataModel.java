package dev.raghav.jobsjunction;

public class DataModel {

    public String title;
    public String notification;
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return notification;
    }

    public void setDescription(String description) {
        this.notification = description;
    }
}
