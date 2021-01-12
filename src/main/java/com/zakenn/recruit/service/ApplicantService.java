package com.zakenn.recruit.service;

import com.zakenn.recruit.repository.Applicant;
import com.zakenn.recruit.repository.ApplicantRepository;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class ApplicantService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ApplicantRepository applicantRepository;

    public void applyForJob(Map<String, String> applicationsData){
        Applicant applicant =  Applicant.builder()
                                    .name(applicationsData.get("name"))
                                    .email(applicationsData.get("email"))
                                    .phoneNumber(applicationsData.get("phoneNumber"))
                                    .resume(applicationsData.get("resume"))
                                    .object(applicationsData.get("object"))
                                    .message(applicationsData.get("message"))
                                    .recruiterMail(applicationsData.get("recruiterMail"))
                                    .build();

        applicantRepository.save(applicant);

        Map<String, Object> vars = Collections.<String, Object>singletonMap("applicant", applicant);
        runtimeService.startProcessInstanceByKey("Recruit_Process", vars);
    }

}
