package com.zakenn.recruit.repository;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
public class Candidature {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String object;

    private String message;

    private String resumeId;

    private String recruiterMail;

    @CreatedDate
    private Date date;

}