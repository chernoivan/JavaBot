package org.samurai.model;

import lombok.Getter;
import lombok.Setter;

public class WeatherModel {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Double temp;

    @Getter
    @Setter
    private Double humidity;

    @Getter
    @Setter
    private String icon;

    @Getter
    @Setter
    private String main;
}
