package com.example.codesnippet.designpattern.factory;


import com.example.codesnippet.designpattern.factory.factories.BasketFactory;
import com.example.codesnippet.designpattern.factory.factories.FootFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * create by whr on 2023-07-06
 */
public class BallFactoryGenerator {

    private static final Map<BallTypeEnum, BallFactory> BALL_FACTORIES = new HashMap<>();

    static {
        BALL_FACTORIES.put(BallTypeEnum.BASKETBALL, new BasketFactory());
        BALL_FACTORIES.put(BallTypeEnum.FOOTBALL, new FootFactory());
    }

    public static BallFactory getFactory(BallTypeEnum ballTypeEnum) {
        return BALL_FACTORIES.get(ballTypeEnum);
    }
}
