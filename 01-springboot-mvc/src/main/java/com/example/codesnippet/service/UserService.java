package com.example.codesnippet.service;

import com.example.codesnippet.converter.UserConverter;
import com.example.codesnippet.dao.entity.UserEntity;
import com.example.codesnippet.dao.mapper.UserMapper;
import com.example.codesnippet.model.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public boolean createUser(UserVO userVO){
        UserEntity userEntity = UserConverter.toDO(userVO);
        return userMapper.insert(userEntity) > 0;
    }

    public UserVO getUser(String userId) {
        UserEntity userEntity = userMapper.selectByUserId(userId);
        return UserConverter.toVO(userEntity);
    }
}
