package com.zakenn.recruit.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ApplicationProcessDto {
    private String nameApplicant;
    private String emailApplicant;
    private String phoneNumberApplicant;
    private String objectMail;
    private String contentMail;
    private String resumeAttachment;
    private String emailRecruiter;
}
