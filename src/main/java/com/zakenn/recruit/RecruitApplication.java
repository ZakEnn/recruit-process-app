package com.zakenn.recruit;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude ={SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@CommonsLog
public class RecruitApplication {

    public static void main(String[] args) {
       // DbSchemaUpdate.main(args);
        SpringApplication.run(RecruitApplication.class, args);
    }

}
