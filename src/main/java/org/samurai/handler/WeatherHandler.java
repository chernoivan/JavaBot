package org.samurai.handler;

import org.samurai.ability.Weather;
import org.samurai.bot.Bot;
import org.samurai.command.ParsedCommand;
import org.telegram.telegrambots.api.objects.Update;

public class WeatherHandler extends AbstractHandler{


    public WeatherHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update){
        String city = parsedCommand.getText();
        if("".equals(city))
            return "You must specify the city. Like this:\n" +
                    "/weather {City}";
        Thread thread = new Thread(new Weather(bot, chatId, city));
        thread.start();
        return "";
    }


}
