package com.zakenn.recruit.service;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@CommonsLog
public class ResumeService {

    @Value("${resume.wsip:http://localhost:8084/ws-resume/ }")
    private String restResumeUri;

    public void storeResume(String applicantName, String resume) {
       log.info("Storing resume ...");
       log.info("applicantName: " + applicantName);
        RestTemplate restTemplate = new RestTemplate();
        String url = restResumeUri.concat("applicant/"+applicantName+"/store-cv");

        log.info("store resume of applicant : " + applicantName);
        log.info("resume base64 = " + resume);
        log.info("trigger post url : " + url);
        restTemplate.postForEntity(url, resume, String.class);
    }

}
