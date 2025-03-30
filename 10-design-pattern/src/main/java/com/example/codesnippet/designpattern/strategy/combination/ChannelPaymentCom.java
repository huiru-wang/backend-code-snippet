package com.example.codesnippet.designpattern.strategy.combination;


import com.example.codesnippet.designpattern.strategy.Order;
import com.example.codesnippet.designpattern.strategy.PaymentStrategy;
import com.example.codesnippet.designpattern.strategy.simple.concrete.AdyenPayment;

import java.util.HashMap;
import java.util.Map;

/**
 * 可以拓展为模板方法
 * 增加：
 * payment, refund, query...
 */
public class ChannelPaymentCom {

    Map<String, PaymentStrategy> paymentStrategyMap = new HashMap<>();

    {
        paymentStrategyMap.put("Adyen", new AdyenPayment());
        paymentStrategyMap.put("Knet", new AdyenPayment());
        paymentStrategyMap.put("PayPal", new AdyenPayment());
    }

    public void doPayment(Order order) {
        PaymentStrategy paymentStrategy = paymentStrategyMap.get(order.getPayType());
        paymentStrategy.payment(order);
    }
}
