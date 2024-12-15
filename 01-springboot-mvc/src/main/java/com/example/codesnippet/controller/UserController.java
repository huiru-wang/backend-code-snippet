package com.example.codesnippet.controller;

import com.alibaba.fastjson.JSON;
import com.example.codesnippet.enums.ErrorConstants;
import com.example.codesnippet.model.ServiceResult;
import com.example.codesnippet.model.request.UserCreateRequest;
import com.example.codesnippet.model.request.UserQueryParam;
import com.example.codesnippet.model.response.UserCreateResponse;
import com.example.codesnippet.model.vo.UserVO;
import com.example.codesnippet.service.UserService;
import com.example.codesnippet.util.IdUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 处理路径参数：/api/user/123456
     */
    @GetMapping("/{userId}")
    public ServiceResult<UserVO> getUserById(@PathVariable String userId) throws InterruptedException {
        UserVO user = userService.getUser(userId);
        TimeUnit.SECONDS.sleep(1);
        return ServiceResult.success(user);
    }

    /**
     * 处理查询字符串参数：/api/user/query?userId={userId}&username={username}
     */
    @GetMapping("/query")
    public ServiceResult<String> query(@RequestParam("userId") String userId, @RequestParam("username") String username) {
        String queryParam = String.format("userId is %s username is %s.", userId, username);
        return ServiceResult.success(queryParam);
    }

    /**
     * 处理对象参数，同「查询字符串参数」自动将查询参数包装为对象：/api/user/queryByParam?userId={userId}&username={username}
     */
    @GetMapping("/queryByParam")
    public ServiceResult<String> query(UserQueryParam userQueryParam) {
        return ServiceResult.success(JSON.toJSONString(userQueryParam));
    }

    /**
     * 获取HttpServletRequest对象，取参数
     */
    @GetMapping("/one")
    public ServiceResult<String> list(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String username = request.getParameter("username");
        String queryParam = String.format("userId is %s username is %s.", userId, username);
        return ServiceResult.success(queryParam);
    }

    /**
     * 接受raw格式的json请求，由@RequestBody完成反序列化
     */
    @PostMapping("/create")
    public ServiceResult<UserCreateResponse> createUser(@RequestBody UserCreateRequest createRequest) {
        UserVO userVO = new UserVO();
        userVO.setUsername(createRequest.getUsername());
        userVO.setPhoneNo(createRequest.getPhoneNo());
        userVO.setEmail(createRequest.getEmail());
        userVO.setPassword(createRequest.getPassword());
        boolean result = userService.createUser(userVO);
        if (result) {
            UserCreateResponse response = new UserCreateResponse();
            response.setUserId(IdUtils.UUID());
            response.setUsername(createRequest.getUsername());
            return ServiceResult.success(response);
        }
        return ServiceResult.fail(ErrorConstants.USER_CREATE_FAIL);
    }
}
