package com.example.codesnippet.designpattern.strategy.combination.concrete;

import com.example.codesnippet.designpattern.strategy.Order;
import com.example.codesnippet.designpattern.strategy.PaymentStrategy;

;

public class KnetPayment implements PaymentStrategy {
    @Override
    public boolean payment(Order order) {
        System.out.println("knet payment " + order.getAmount());
        return true;
    }
}
