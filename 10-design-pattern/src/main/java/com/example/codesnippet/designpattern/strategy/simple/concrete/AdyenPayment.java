package com.example.codesnippet.designpattern.strategy.simple.concrete;


import com.example.codesnippet.designpattern.strategy.Order;
import com.example.codesnippet.designpattern.strategy.PaymentStrategy;

public class AdyenPayment implements PaymentStrategy {
    @Override
    public boolean payment(Order order) {
        System.out.println("Adyen Pay");
        return true;
    }
}