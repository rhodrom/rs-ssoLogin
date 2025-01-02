package com.uyun.rs.openapi.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserVO {
    private String iamRequestId;
    private String iamRemoteUser;
    private String iamRemotePwd;
    private String loginName;
    private String orgId;
    private List<String> role;
    private String fullName;
    private String password;
    private String status;
    private String uid;
}
