package com.zakenn.recruit.service;

import com.zakenn.recruit.repository.ApplicantRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@CommonsLog
public class RecruiterService {
    @Autowired
    TaskService taskService;

    public void reviewResume(String recruiterMail, String processInstanceId, Boolean isAccepted){
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskAssignee(recruiterMail)
                .singleResult();

        log.info("task begin :  " + task);
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("reviewCvOutcome", isAccepted);
        taskService.complete(task.getId(), taskVariables);
        log.info("task end :  " + task);

    }
}
