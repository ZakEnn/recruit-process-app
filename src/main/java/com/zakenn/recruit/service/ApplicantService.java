package com.zakenn.recruit.service;

import com.zakenn.recruit.dto.ApplicationProcessDto;
import com.zakenn.recruit.repository.Applicant;
import com.zakenn.recruit.repository.ApplicantRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@CommonsLog
public class ApplicantService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    RepositoryService repositoryService;

    public ResponseEntity<String> applyForJob(ApplicationProcessDto applicationsData){
        Applicant applicant =  Applicant.builder()
                                    .name(applicationsData.getNameApplicant())
                                    .email(applicationsData.getEmailApplicant())
                                    .phoneNumber(applicationsData.getPhoneNumberApplicant())
                                    .object(applicationsData.getObjectMail())
                                    .message(applicationsData.getContentMail())
                                    .recruiterMail(applicationsData.getEmailRecruiter())
                                    .build();

        applicantRepository.save(applicant);

        Map<String, Object> vars = Map.of("applicant", applicant, "resume", applicationsData.getResumeAttachment());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("recruit_Process", vars);

        log.info("END  processInstanceId : " + processInstance.getProcessInstanceId());
        return ResponseEntity.status(HttpStatus.CREATED).body(processInstance.getProcessInstanceId());
    }

}
