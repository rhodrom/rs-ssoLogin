package com.uyun.rs.api;

import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;


public interface UserOpenApi {
    @Post("http://10.1.58.224/tenant/openapi/v2/users/save")
    JSONObject UserCreate(@JSONBody JSONObject jsonObject);

    @Post("http://10.1.58.224/tenant/openapi/v2/departs/user/add")
    Boolean UserDepartsCreate(@JSONBody JSONObject jsonObject);

    @Post("http://10.1.58.224/tenant/openapi/v2/roles/users/relate")
    Boolean UserRoleCreate(@JSONBody JSONObject jsonObject);

    @Post("http://10.1.58.224/tenant/openapi/v2/users/update")
    Boolean UserUpdate(@JSONBody JSONObject jsonObject);

    @Post("http://10.1.58.224/tenant/openapi/v2/users/delete")
    Boolean UserDelete(@JSONBody String user_id);
}
