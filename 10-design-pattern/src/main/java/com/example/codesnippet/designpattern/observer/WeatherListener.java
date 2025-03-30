package com.example.codesnippet.designpattern.observer;

public interface WeatherListener {
    void onTemperatureUp(String temperature);

    void onTemperatureDown(String temperature);
}
