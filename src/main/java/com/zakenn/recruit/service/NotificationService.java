package com.zakenn.recruit.service;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@CommonsLog
public class NotificationService {
    public void sendMessage(String applicant, String response){
        log.info("message to send : " + response);
        log.info("applicant : " + applicant);
    }
}
