package com.example.codesnippet.designpattern.strategy.simple.concrete;

import com.example.codesnippet.designpattern.strategy.Order;
import com.example.codesnippet.designpattern.strategy.PaymentStrategy;;

public class PayPalPayment implements PaymentStrategy {
    @Override
    public boolean payment(Order order) {
        System.out.println("PayPal Pay");
        return true;
    }
}
