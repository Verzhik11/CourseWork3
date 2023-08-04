package pro.sky.telegrambot.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table (name = "notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "id_telegram")
    private long idTelegram;
    @Column(name = "user_name")
    private String userName;
    private String notification;
    @Column(name = "notification_send_time")
    private LocalDateTime notificationSendTime;

    public NotificationTask() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdTelegram() {
        return idTelegram;
    }

    public void setIdTelegram(long idTelegram) {
        this.idTelegram = idTelegram;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public LocalDateTime getNotificationSendTime() {
        return notificationSendTime;
    }

    public void setNotificationSendTime(LocalDateTime notificationSendTime) {
        this.notificationSendTime = notificationSendTime;
    }
}
