package com.example.codesnippet.designpattern.template.menu;


import com.example.codesnippet.designpattern.template.CookTemplate;

public class InstantNoodles extends CookTemplate {

    protected void prepareMaterials() {
        System.out.println("unpack an Instant Noodles");
    }

    protected void cooking() {
        System.out.println("pour hot water waiting 10 mins");
    }

    protected void plating() {
        System.out.println("instant noodles is done");
    }
}
