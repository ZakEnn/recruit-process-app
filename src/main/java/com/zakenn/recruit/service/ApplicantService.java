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
    private ApplicantRepository applicantRepository;

    public Applicant saveAndGetApplicant(ApplicationProcessDto applicationsProcessDto) {
        Applicant applicant =  Applicant.builder()
                .name(applicationsProcessDto.getNameApplicant())
                .email(applicationsProcessDto.getEmailApplicant())
                .phoneNumber(applicationsProcessDto.getPhoneNumberApplicant())
                .object(applicationsProcessDto.getObjectMail())
                .message(applicationsProcessDto.getContentMail())
                .recruiterMail(applicationsProcessDto.getEmailRecruiter())
                .build();

        return applicantRepository.save(applicant);
    }


}
