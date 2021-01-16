package com.zakenn.recruit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zakenn.recruit.utils.ResponseUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@CommonsLog
public class NotificationService {
    public Map<String,String> sendMessage(String name, String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> result = new HashMap<>();
        result.put("greeting", "Hi " + name);
        result.putAll(objectMapper.readValue(response, Map.class));
        log.info(result);
        return result;
    }
}
