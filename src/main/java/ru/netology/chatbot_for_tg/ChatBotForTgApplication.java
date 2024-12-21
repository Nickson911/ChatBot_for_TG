package ru.netology.chatbot_for_tg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.netology.chatbot_for_tg.service.TelegramBot;

@SpringBootApplication
public class ChatBotForTgApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(ChatBotForTgApplication.class, args);
        
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(context.getBean(TelegramBot.class));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
