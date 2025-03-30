package com.example.codesnippet.designpattern.strategy;


import com.example.codesnippet.designpattern.strategy.combination.ChannelPaymentCom;
import com.example.codesnippet.designpattern.strategy.simple.ChannelPayment;
import com.example.codesnippet.designpattern.strategy.simple.concrete.AdyenPayment;

public class App {
    public static void main(String[] args) {
        Order order = new Order();
        order.setAmount("1.2");
        order.setPayType("Adyen");

        // simple
        AdyenPayment adyenPayment = new AdyenPayment();
        ChannelPayment channelPayment = new ChannelPayment(adyenPayment);
        channelPayment.doPayment(order);

        // combination:
        ChannelPaymentCom channelPaymentCom = new ChannelPaymentCom();
        channelPaymentCom.doPayment(order);
    }
}
