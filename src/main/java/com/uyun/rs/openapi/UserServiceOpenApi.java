package com.uyun.rs.openapi;

import com.alibaba.fastjson.JSONObject;
import com.uyun.rs.openapi.vo.UserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserServiceOpenApi {

    @PostMapping("/UserCreateService")
    JSONObject UserCreateService(@RequestBody UserVO userVO);

    @PostMapping("/UserUpdateService")
    JSONObject UserUpdateService(@RequestBody UserVO userVO);

    @PostMapping("/UserDeleteService")
    JSONObject UserDeleteService(@RequestBody UserVO userVO);
}
