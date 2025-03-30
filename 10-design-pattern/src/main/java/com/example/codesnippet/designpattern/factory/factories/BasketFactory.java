package com.example.codesnippet.designpattern.factory.factories;


import com.example.codesnippet.designpattern.factory.Ball;
import com.example.codesnippet.designpattern.factory.BallFactory;
import com.example.codesnippet.designpattern.factory.balls.BasketBall;

/**
 * create by whr on 2023-07-06
 */
public class BasketFactory implements BallFactory {

    @Override
    public Ball produce() {
        BasketBall basketBall = new BasketBall();
        System.out.println("Produce basketball");
        return basketBall;
    }
}
