package com.uyun.rs.openapi.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uyun.rs.api.UserOpenApi;
import com.uyun.rs.openapi.UserServiceOpenApi;
import com.uyun.rs.openapi.vo.UserVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

public class UserServiceOpenApiImpl implements UserServiceOpenApi {

    @Value("${uyun.tenantId}")
    private String tenantId;

    @Resource
    private UserOpenApi userOpenApi;

    @Override
    public JSONObject UserCreateService(@RequestBody UserVO userVO) {

        JSONObject jsonObject_user = new JSONObject();
        jsonObject_user.put("realname",userVO.getFullName());
        jsonObject_user.put("account",userVO.getIamRemoteUser());
        jsonObject_user.put("passwd",userVO.getIamRemotePwd());
        JSONObject jsonObject_userRe = userOpenApi.UserCreate(jsonObject_user);

        String userId = jsonObject_userRe.getString("userId");
        JSONObject jsonObject_depart = new JSONObject();
        jsonObject_depart.put("departId",userVO.getOrgId());
        jsonObject_depart.put("tenantId",tenantId);
        jsonObject_depart.put("userIds",userId);
        userOpenApi.UserDepartsCreate(jsonObject_depart);

        for (String role:userVO.getRole()
             ) {
           JSONObject jsonObject_role = new JSONObject();
           JSONArray jsonArray_userid = new JSONArray();
           jsonArray_userid.add(userId);
           jsonObject_role.put("roleId",role);
           jsonObject_role.put("userIds",jsonArray_userid);
           userOpenApi.UserRoleCreate(jsonObject_role);
        }

        JSONObject jsonObject_re = new JSONObject();
        jsonObject_re.put("iamRequestId",userVO.getIamRequestId());
        jsonObject_re.put("uid",userId);
        jsonObject_re.put("resultCode","0");
        jsonObject_re.put("message","success");
        return jsonObject_re;
    }

    @Override
    public JSONObject UserUpdateService(UserVO userVO) {
        JSONObject jsonObject_update = new JSONObject();
        jsonObject_update.put("userId",userVO.getUid());
        jsonObject_update.put("realname",userVO.getFullName());
        userOpenApi.UserUpdate(jsonObject_update);

        JSONObject jsonObject_re = new JSONObject();
        jsonObject_re.put("iamRequestId",userVO.getIamRequestId());
        jsonObject_re.put("resultCode","0");
        jsonObject_re.put("message","success");
        return jsonObject_re;
    }

    @Override
    public JSONObject UserDeleteService(UserVO userVO) {
        userOpenApi.UserDelete(userVO.getUid());
        JSONObject jsonObject_re = new JSONObject();
        jsonObject_re.put("iamRequestId",userVO.getIamRequestId());
        jsonObject_re.put("resultCode","0");
        jsonObject_re.put("message","success");
        return jsonObject_re;
    }



}
