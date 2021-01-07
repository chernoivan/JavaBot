package org.samurai;

import org.apache.log4j.Logger;
import org.samurai.bot.Bot;
import org.samurai.service.MessageReciever;
import org.samurai.service.MessageSender;
import org.telegram.telegrambots.ApiContextInitializer;

public class App {
    private static final Logger log = Logger.getLogger(App.class);
    private static final int PRIORITY_FOR_SENDER = 1;
    private static final int PRIORITY_FOR_RECEIVER = 3;
    private static final String BOT_ADMIN = "382545190";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Bot test_samurai_bot = new Bot("test_samurai_bot", "1521056118:AAHeL22P6t2Q6E7F4hVYht-3u5dTbYas-nA");

        MessageReciever messageReciever = new MessageReciever(test_samurai_bot);
        MessageSender messageSender = new MessageSender(test_samurai_bot);

        test_samurai_bot.botConnect();

        Thread receiver = new Thread(messageReciever);
        receiver.setDaemon(true);
        receiver.setName("MsgReciever");
        receiver.setPriority(PRIORITY_FOR_RECEIVER);
        receiver.start();

        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.setPriority(PRIORITY_FOR_SENDER);
        sender.start();
    }
}
