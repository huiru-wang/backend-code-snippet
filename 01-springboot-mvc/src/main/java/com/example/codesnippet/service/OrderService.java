package com.example.codesnippet.service;

import com.example.codesnippet.dao.entity.OrderDetailEntity;
import com.example.codesnippet.dao.entity.OrderEntity;
import com.example.codesnippet.dao.mapper.OrderDetailMapper;
import com.example.codesnippet.dao.mapper.OrderMapper;
import com.example.codesnippet.model.vo.UserOrderVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Transactional
    public boolean createOrder(UserOrderVO userOrderVO) {
        // TODO
        OrderEntity orderEntity = new OrderEntity();
        orderMapper.insert(orderEntity);

        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        orderDetailMapper.insert(orderDetailEntity);

        return true;
    }

}
