package com.zakenn.recruit.service;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@CommonsLog
public class ResumeService {

    @Value("${resume.wsip}")
    private String restResumeUri;

    public void storeResume(String resumeId, String resumeB64) {
        log.info("Storing resume ...");
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> resumeData = new HashMap<>();
        resumeData.put("resumeId", resumeId);
        resumeData.put("resumeB64", resumeB64);
        String url = restResumeUri.concat("store-cv");

        HttpHeaders headers = new HttpHeaders();
        headers.add("TIMESTAMP", LocalDateTime.now().toString());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(resumeData,headers);
        log.info("post request : " + url + " with data : " + resumeData);
        restTemplate.postForObject(url, entity, Map.class);
    }

}
