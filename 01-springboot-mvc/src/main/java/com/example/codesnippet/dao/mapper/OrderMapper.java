package com.example.codesnippet.dao.mapper;

import com.example.codesnippet.dao.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

    int insert(@Param("order") OrderEntity order);

    int update(@Param("order") OrderEntity order);

    OrderEntity selectByOrderId(@Param("orderId") String orderId);
}
