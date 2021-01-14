package com.zakenn.recruit.service;

import com.zakenn.recruit.dto.ApplicationProcessDto;
import com.zakenn.recruit.repository.Applicant;
import com.zakenn.recruit.repository.ApplicantRepository;
import com.zakenn.recruit.utils.ResponseUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
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

    @Autowired
    TaskService taskService;

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

        Map<String, Object> vars = Map.of("applicant", applicant, "resume", applicationsData.getResumeAttachment(), "response", ResponseUtils.defaultResponseData);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("recruit_Process", vars);

        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .taskAssignee("recruiter@gmail.com")
                .singleResult();
         log.info("task first :  " + task);
        // Completing the phone interview with success should trigger two new tasks
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("reviewCvOutcome", true);
        taskService.complete(task.getId(), taskVariables);
        log.info("task end :  " + task);

        log.info("END  processInstanceId : " + processInstance.getProcessInstanceId());
        return ResponseEntity.status(HttpStatus.CREATED).body(processInstance.getProcessInstanceId());
    }

}
