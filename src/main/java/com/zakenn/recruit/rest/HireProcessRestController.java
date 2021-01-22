package com.zakenn.recruit.rest;

import com.zakenn.recruit.dto.ApplicationProcessDto;
import com.zakenn.recruit.dto.ReviewTaskDto;
import com.zakenn.recruit.service.ProcessService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @GetMapping(value = "/tasks")
    public ResponseEntity<List<Map<String, String>>> getTasksByAssignee(@RequestParam String assignee) {
        List<Map<String, String>> tasks = processService.getTasksByAssignee(assignee);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @GetMapping(value = "/applications")
    public ResponseEntity<List<ApplicationProcessDto>> getApplicationByAssignee(@RequestParam String assignee) {
      List<ApplicationProcessDto> applicationProcessDtos =  processService.getApplicationsByAssignee(assignee);
      return ResponseEntity.status(HttpStatus.OK).body(applicationProcessDtos);
    }

}

