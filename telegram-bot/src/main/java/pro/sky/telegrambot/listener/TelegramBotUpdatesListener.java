package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final NotificationTaskRepository notificationTaskRepository;

    public TelegramBotUpdatesListener(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String txt = update.message().text();
            long chatId = update.message().chat().id();
            String messageText = "Добро пожаловать в Телеграм-напоминалку для ДЗ";
            if (txt.equals("/start")) {
                SendMessage message = new SendMessage(chatId, messageText);
                SendResponse response = telegramBot.execute(message);
            } else {
                create(updates);
            }
            List<NotificationTask> notificationTasks = getNowNotificationTasks();
                for (NotificationTask notificationTask : notificationTasks) {
                    SendMessage sendMessage = new SendMessage(notificationTask.getIdTelegram(),
                            notificationTask.getNotification());
                    SendResponse response = telegramBot.execute(sendMessage);
                }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Transactional
    public NotificationTask create (List<Update> updates) {
        NotificationTask notificationTask = new NotificationTask();
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String txt = update.message().text();
            long chatId = update.message().chat().id();
            Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
            Matcher matcher = pattern.matcher(txt);
            if (matcher.matches()) {
            String date = matcher.group(1);
            String item = matcher.group(3);
            notificationTask.setNotification(item);
            notificationTask.setIdTelegram(chatId);
            notificationTask.setUserName(update.message().from().username());
            notificationTask.setNotificationSendTime(LocalDateTime
                    .parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        } else {
                notificationTask.setNotification(null);
                notificationTask.setIdTelegram(0);
            }
        });
        return notificationTaskRepository.save(notificationTask);
    }
    @Scheduled(cron = "0 0/1 * * * *")
    public List<NotificationTask> getNowNotificationTasks() {
        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        return notificationTaskRepository.findNotificationTaskByNotificationSendTime(localDateTime);

    }

}
