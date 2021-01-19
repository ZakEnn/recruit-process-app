package com.zakenn.recruit.service;

import com.zakenn.recruit.dto.ApplicationProcessDto;
import com.zakenn.recruit.repository.Candidature;
import com.zakenn.recruit.repository.CandidatureRepository;
import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@CommonsLog
public class ApplicantService {
    @Autowired
    private CandidatureRepository candidatureRepository;

    @Value("${resume.wsip:http://localhost:8084/ws-resume/}")
    private String restResumeUri;

    public Candidature getApplicationFromProcessDto(ApplicationProcessDto applicationsProcessDto){
       return  Candidature.builder()
                .name(applicationsProcessDto.getNameApplicant())
                .email(applicationsProcessDto.getEmailApplicant())
                .phoneNumber(applicationsProcessDto.getPhoneNumberApplicant())
                .object(applicationsProcessDto.getObjectMail())
                .message(applicationsProcessDto.getContentMail())
                .resumeId(UUID.randomUUID().toString())
                .recruiterMail(applicationsProcessDto.getEmailRecruiter())
                .build();
    }

    public Candidature saveApplication(Candidature candidature, String processId) {
        candidature.setProcessId(processId);
        return candidatureRepository.save(candidature);
    }


    public ApplicationProcessDto candidatureToApplicationProcessDto(Candidature candidature) {
        String wsipResume = restResumeUri.trim() + candidature.getResumeId();
        ApplicationProcessDto applicationProcessDto = new ApplicationProcessDto();
        applicationProcessDto.setEmailApplicant(candidature.getEmail());
        applicationProcessDto.setNameApplicant(candidature.getName());
        applicationProcessDto.setContentMail(candidature.getMessage());
        applicationProcessDto.setObjectMail(candidature.getObject());
        applicationProcessDto.setPhoneNumberApplicant(candidature.getPhoneNumber());
        applicationProcessDto.setEmailRecruiter(candidature.getRecruiterMail());
        applicationProcessDto.setResumeAttachment(wsipResume);
        return applicationProcessDto;
    }

    public Optional<Candidature> findByProcessId(String processInstanceId) {
        return candidatureRepository.findByProcessId(processInstanceId);
    }
}
