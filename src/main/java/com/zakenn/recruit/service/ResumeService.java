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

    public void storeResume(String resumeId, String resumeB64) {
        log.info("Storing resume ...");
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> resumeData = new HashMap<>();
        resumeData.put("resumeId", resumeId);
        resumeData.put("resumeB64", resumeB64);
        String url = restResumeUri.concat("/store-cv");
        restTemplate.postForEntity(url, resumeData, Map.class);
    }

}
