package com.zakenn.recruit.rest;

import com.zakenn.recruit.dto.ApplicationProcessDto;
import com.zakenn.recruit.dto.ReviewTaskDto;
import com.zakenn.recruit.repository.Applicant;
import com.zakenn.recruit.repository.ApplicantRepository;
import com.zakenn.recruit.service.ApplicantService;
import com.zakenn.recruit.service.ProcessService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class HireProcessRestController {

    @Autowired
    ProcessService processService;

    @PostMapping(value = "/start-process/{processName}")
    public ResponseEntity<String> startHireProcess(@PathVariable String processName, @RequestBody ApplicationProcessDto data) {
        String processId = processService.applyForJob(data, processName);
        return ResponseEntity.status(HttpStatus.OK).body(processId);
    }

    @PostMapping(value = "/process/{processId}/review-resume")
    public ResponseEntity<?> reviewResumeTask(@PathVariable String processId, @RequestBody ReviewTaskDto reviewTaskDto) {
       processService.completeReviewTask(processId, reviewTaskDto);
        return ResponseEntity.status(HttpStatus.OK).body(processId);
    }
}