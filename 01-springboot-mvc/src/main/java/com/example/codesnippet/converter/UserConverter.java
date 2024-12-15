package com.example.codesnippet.converter;

import com.example.codesnippet.dao.entity.UserEntity;
import com.example.codesnippet.model.vo.UserVO;
import com.example.codesnippet.util.IdUtils;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class UserConverter {

    public static UserVO toVO(UserEntity userEntity) {
        UserVO userVO = new UserVO();
        if (null == userEntity) {
            return userVO;
        }
        userVO.setUserId(userEntity.getUserId());
        userVO.setUsername(userEntity.getUsername());
        userVO.setPhoneNo(userEntity.getPhoneNo());
        userVO.setEmail(userEntity.getEmail());
        return userVO;
    }

    public static UserEntity toDO(@NotNull UserVO userVO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(IdUtils.UUID());
        userEntity.setUsername(userVO.getUsername());
        userEntity.setPassword(userEntity.getPassword());
        userEntity.setPhoneNo(userVO.getPhoneNo());
        userEntity.setEmail(userVO.getEmail());
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setUpdatedAt(LocalDateTime.now());
        return userEntity;
    }

}
