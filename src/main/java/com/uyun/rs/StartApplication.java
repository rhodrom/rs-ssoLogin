package com.uyun.rs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.uyun.rs", "uyun.whale"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ServletComponentScan
@EnableWebSecurity
public class StartApplication {

    public static void main(String[] args) {
        String workdir = System.getProperty("install.dir", System.getProperty("user.dir"));
        System.setProperty("work.dir", workdir);
        new SpringApplicationBuilder(StartApplication.class).main(StartApplication.class)
                .properties("spring.config.name:bootstrap,rs-ssoLogin").build().run(args);
    }

}