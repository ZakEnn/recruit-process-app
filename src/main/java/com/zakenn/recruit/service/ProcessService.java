package com.zakenn.recruit.service;

import com.zakenn.recruit.dto.ApplicationProcessDto;
import com.zakenn.recruit.dto.ReviewTaskDto;
import com.zakenn.recruit.exceptions.ProcessException;
import com.zakenn.recruit.repository.Candidature;
import com.zakenn.recruit.utils.ResponseUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

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
        Candidature application = applicantService.getApplicationFromProcessDto(applicationsProcessDto);
        Map<String, Object> vars = Map.of("application", application, "resumeB64", applicationsProcessDto.getResumeAttachment(), "response", ResponseUtils.defaultResponseData);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processName, vars);
        applicantService.saveApplication(application, processInstance.getProcessInstanceId());
        log.info("pending  processInstanceId : " + processInstance.getProcessInstanceId());
        return processInstance.getProcessInstanceId();
    }

    public void completeReviewTask(String processInstanceId, ReviewTaskDto reviewTaskDto){
        log.info("Start task reviewCvOutcome  with processId : " + processInstanceId + " and data :  " + reviewTaskDto);
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskAssignee(reviewTaskDto.getRecruiterMail())
                .singleResult();

        if(task == null) throw new ProcessException( "Review task not found for processId : " + processInstanceId);

        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("reviewCvOutcome", reviewTaskDto.getIsAccepted());
        taskService.complete(task.getId(), taskVariables);
        log.info("End task :  " + task);
        log.info("***************  End processID :  " + processInstanceId + " ********************");
    }

    public List<ApplicationProcessDto> getApplicationsByAssignee(String assignee) {
        log.info("get applications by assignee = " + assignee);
        return taskService.createTaskQuery().taskAssignee(assignee)
                .orderByTaskCreateTime().desc().list()
                .stream().map(task -> {
                    Optional<Candidature> candidature = applicantService.findByProcessId(task.getProcessInstanceId());
                    return applicantService.candidatureToApplicationProcessDto(candidature.orElse(new Candidature()));
                })
                .filter(candidature -> StringUtils.hasLength(candidature.getEmailApplicant()))
                .collect(Collectors.toList());
    }

    public List<Map<String, String>> getTasksByAssignee(String assignee) {
        log.info("get tasks by assignee = " + assignee);
        List<Task> taskInstances = taskService.createTaskQuery().taskAssignee(assignee)
                .orderByTaskCreateTime().desc().list();

        List<Map<String, String>> tasks = new ArrayList<>();
        for (Task task : taskInstances) {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("processId", task.getProcessInstanceId());
            map.put("taskId", task.getId());
            map.put("taskDefinitionKey", task.getTaskDefinitionKey());
            map.put("taskName", task.getName());
            tasks.add(map);
        }
        return tasks;
    }
}
