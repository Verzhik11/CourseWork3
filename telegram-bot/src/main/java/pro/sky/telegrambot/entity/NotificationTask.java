package pro.sky.telegrambot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
@Entity
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long id_telegram;
    private String user_name;
    private String notification;
    private LocalDateTime notification_send_time;

    public NotificationTask() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_telegram() {
        return id_telegram;
    }

    public void setId_telegram(long id_telegram) {
        this.id_telegram = id_telegram;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public LocalDateTime getNotification_send_time() {
        return notification_send_time;
    }

    public void setNotification_send_time(LocalDateTime notification_send_time) {
        this.notification_send_time = notification_send_time;
    }
}
