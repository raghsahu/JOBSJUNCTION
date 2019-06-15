package dev.raghav.jobsjunction;

import java.io.Serializable;

public class NotifiModel implements Serializable {

    private String notificationId;
    private String category;
    private String notification;
    private String title;
    private String date;
    private String company;

    public NotifiModel(String notificationId, String category, String notification, String title, String date, String company) {
        this.notificationId = notificationId;
        this.category = category;
        this.notification = notification;
        this.title = title;
        this.date = date;
        this.company=company;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
