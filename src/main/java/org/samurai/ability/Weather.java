package org.samurai.ability;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.samurai.bot.Bot;
import org.samurai.model.WeatherModel;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather implements Runnable {
    //9c3bf520455704da6b5f608a7ac97286
    private final String WRONG_INPUT_MESSAGE = "Wrong input. City is not found.";
    private static final Logger log = Logger.getLogger(Weather.class);
    WeatherModel model = new WeatherModel();

    Bot bot;
    String chatID;
    String city;

    public Weather(Bot bot, String chatID, String city) {
        this.bot = bot;
        this.chatID = chatID;
        this.city = city;
        log.debug("CREATE. " + toString());
    }

    @Override
    public void run() {
        log.info("RUN. " + toString());
        try {
            bot.sendQueue.add(getWeather(city, model));
        } catch (IOException e) {
            log.error(e.getMessage());
            bot.sendQueue.add(getErrorMessage());
        }
        log.info("FINISH. " + toString());
    }

    public SendMessage getWeather(String message, WeatherModel model) throws IOException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);

        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&appid=9c3bf520455704da6b5f608a7ac97286");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }
//        sendMessage.setText(result);

        JSONObject object = new JSONObject(result);

        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));
        }

        sendMessage.setText("City: " + model.getName() + "\n" +
                "Temperature: " + model.getTemp() + "C" +"\n" +
                "Humidity: " + model.getHumidity() + "%" + "\n" +
                "Main: " + model.getMain() + "\n" +
                "http://openweathermap.org/img/wn/" + model.getIcon() +".png"
        );

        return sendMessage;



    }

    private SendMessage getErrorMessage() {
        return new SendMessage(chatID, WRONG_INPUT_MESSAGE);
    }


}
