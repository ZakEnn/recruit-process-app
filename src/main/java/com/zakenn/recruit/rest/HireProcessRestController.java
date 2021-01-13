package com.zakenn.recruit.rest;

import com.zakenn.recruit.dto.ApplicationProcessDto;
import com.zakenn.recruit.repository.Applicant;
import com.zakenn.recruit.repository.ApplicantRepository;
import com.zakenn.recruit.service.ApplicantService;
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
    ApplicantService applicantService;

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/start-hire-process", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startHireProcess(@RequestBody ApplicationProcessDto data) {
        applicantService.applyForJob(data);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}