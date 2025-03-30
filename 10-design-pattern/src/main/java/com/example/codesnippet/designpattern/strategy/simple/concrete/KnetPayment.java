package com.example.codesnippet.designpattern.strategy.simple.concrete;

import com.example.codesnippet.designpattern.strategy.Order;
import com.example.codesnippet.designpattern.strategy.PaymentStrategy;;

public class KnetPayment implements PaymentStrategy {
    @Override
    public boolean payment(Order order) {
        System.out.println("Knet Pay");
        return true;
    }
}
