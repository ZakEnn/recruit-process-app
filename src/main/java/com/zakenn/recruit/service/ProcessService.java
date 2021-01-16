package com.zakenn.recruit.service;

import com.zakenn.recruit.dto.ApplicationProcessDto;
import com.zakenn.recruit.dto.ReviewTaskDto;
import com.zakenn.recruit.repository.Applicant;
import com.zakenn.recruit.utils.ResponseUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@CommonsLog
public class ProcessService {
    @Autowired
    ApplicantService applicantService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public String applyForJob(ApplicationProcessDto applicationsProcessDto, String processName){
        log.info("Start  process : " + processName + " with data : " + applicationsProcessDto);
        Applicant applicant = applicantService.saveAndGetApplicant(applicationsProcessDto);
        Map<String, Object> vars = Map.of("applicant", applicant, "resume", applicationsProcessDto.getResumeAttachment(), "response", ResponseUtils.defaultResponseData);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processName, vars);
        log.info("End processInstanceId : " + processInstance.getProcessInstanceId());
        return processInstance.getProcessInstanceId();
    }

    public void completeReviewTask(String processInstanceId, ReviewTaskDto reviewTaskDto){
        log.info("Start task reviewCvOutcome  with processId : " + processInstanceId + " and data :  " + reviewTaskDto);
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskAssignee(reviewTaskDto.getRecruiterMail())
                .singleResult();

        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("reviewCvOutcome", reviewTaskDto.getIsAccepted());
        taskService.complete(task.getId(), taskVariables);
        log.info("End task :  " + task);
    }
}
