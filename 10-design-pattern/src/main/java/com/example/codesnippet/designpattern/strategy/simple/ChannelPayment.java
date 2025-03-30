package com.example.codesnippet.designpattern.strategy.simple;


import com.example.codesnippet.designpattern.strategy.Order;
import com.example.codesnippet.designpattern.strategy.PaymentStrategy;

public class ChannelPayment {

    private PaymentStrategy paymentStrategy;

    public ChannelPayment(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void doPayment(Order order) {
        System.out.println("Do Payment");
        this.paymentStrategy.payment(order);
    }
}
