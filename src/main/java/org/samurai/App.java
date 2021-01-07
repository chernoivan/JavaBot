package org.samurai;

import org.apache.log4j.Logger;
import org.samurai.bot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;

public class App {
    private static final Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Bot test_samurai_bot = new Bot("test_samurai_bot", "1521056118:AAHeL22P6t2Q6E7F4hVYht-3u5dTbYas-nA");
        test_samurai_bot.botConnect();
    }
}
