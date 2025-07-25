package com.example.codesnippet.designpattern.observer;


import com.example.codesnippet.designpattern.observer.listeners.GrandPa;

public class App {

    public static void main(String[] args) {
        Weather weather = new Weather();

        // grandMa SPI订阅
        // grandPa手动订阅
        GrandPa grandPa = new GrandPa();
        weather.addSubscriber(grandPa);

        weather.riseUp("35");

        weather.coolDown("12");

    }
}
