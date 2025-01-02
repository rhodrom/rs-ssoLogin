package com.uyun.rs.config;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import uyun.tenant.serviceapi.ApiKeyServiceApi;
import uyun.tenant.serviceapi.AppServiceApi;
import uyun.tenant.serviceapi.dto.ApiKeyDTO;


@Component
public class AppIdConfig implements ApplicationRunner {
    @Reference
    private AppServiceApi appServiceApi;
    @Reference
    private ApiKeyServiceApi apiKeyServiceApi;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String ADMIN_TENANT_ID = "e10adc3949ba59abbe56e057f20f88dd";
        ApiKeyDTO apiKeyDTO = apiKeyServiceApi.listByUserId(ADMIN_TENANT_ID).get(0);
        String adminApikey = apiKeyDTO.getKey();
        String adminTenantId = apiKeyDTO.getTenantId();
        String adminUserId = apiKeyDTO.getUserId();
        System.setProperty("adminApikey", adminApikey);
        System.setProperty("adminTenantId", adminTenantId);
        System.setProperty("adminUserId", adminUserId);
    }
}
