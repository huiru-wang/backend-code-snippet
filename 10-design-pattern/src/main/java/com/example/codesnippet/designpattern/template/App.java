package com.example.codesnippet.designpattern.template;


import com.example.codesnippet.designpattern.template.menu.InstantNoodles;
import com.example.codesnippet.designpattern.template.menu.KungPaoChicken;

public class App {

    public static void main(String[] args) {
        KungPaoChicken kungPaoChicken = new KungPaoChicken();
        kungPaoChicken.cook();
        System.out.println("==================================");
        InstantNoodles instantNoodles = new InstantNoodles();
        instantNoodles.cook();
    }
}
