package com.example.codesnippet.designpattern.factory.factories;

import com.example.codesnippet.designpattern.factory.Ball;
import com.example.codesnippet.designpattern.factory.BallFactory;
import com.example.codesnippet.designpattern.factory.balls.FootBall;

/**
 * create by whr on 2023-07-06
 */
public class FootFactory implements BallFactory {

    @Override
    public Ball produce() {
        FootBall footBall = new FootBall();
        System.out.println("Produce football");
        return footBall;
    }
}
