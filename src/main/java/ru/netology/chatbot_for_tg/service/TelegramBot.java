package ru.netology.chatbot_for_tg.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.netology.chatbot_for_tg.config.BotConfig;
import ru.netology.chatbot_for_tg.service.GptService;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final GptService gptService;

    public TelegramBot(BotConfig config, GptService gptService) {
        this.config = config;
        this.gptService = gptService;
    }

    @Override
    public String getBotUsername() {
        return config.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        var chatId = update.getMessage().getChatId();
        var messageText = update.getMessage().getText();

        try {
            // Get response from GPT
            String gptResponse = gptService.getGptResponse(messageText);
            
            // Send response to user
            SendMessage message = new SendMessage();
            message.setChatId(chatId.toString());
            message.setText(gptResponse);
            
            execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Error occurred while sending message: " + e.getMessage());
        }
    }
}
