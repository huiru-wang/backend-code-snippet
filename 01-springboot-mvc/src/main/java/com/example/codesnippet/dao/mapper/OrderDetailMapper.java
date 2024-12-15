package com.example.codesnippet.dao.mapper;

import com.example.codesnippet.dao.entity.OrderDetailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface OrderDetailMapper {

    int insert(@Param("orderDetail") OrderDetailEntity orderDetailEntity);

    OrderDetailEntity selectByOrderId(@Param("orderId") String orderId);
}
