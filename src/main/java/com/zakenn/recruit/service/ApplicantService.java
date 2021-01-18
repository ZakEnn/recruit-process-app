package com.zakenn.recruit.service;

import com.zakenn.recruit.dto.ApplicationProcessDto;
import com.zakenn.recruit.repository.Candidature;
import com.zakenn.recruit.repository.CandidatureRepository;
import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@CommonsLog
public class ApplicantService {
    @Autowired
    private CandidatureRepository candidatureRepository;

    public Candidature saveAndGetApplicant(ApplicationProcessDto applicationsProcessDto) {

        Candidature candidature =  Candidature.builder()
                .name(applicationsProcessDto.getNameApplicant())
                .email(applicationsProcessDto.getEmailApplicant())
                .phoneNumber(applicationsProcessDto.getPhoneNumberApplicant())
                .object(applicationsProcessDto.getObjectMail())
                .message(applicationsProcessDto.getContentMail())
                .resumeId(UUID.randomUUID().toString())
                .recruiterMail(applicationsProcessDto.getEmailRecruiter())
                .build();

        return candidatureRepository.save(candidature);
    }


}
