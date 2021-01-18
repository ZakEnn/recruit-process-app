package com.zakenn.recruit.service;

import com.zakenn.recruit.dto.ApplicationProcessDto;
import com.zakenn.recruit.dto.ReviewTaskDto;
import com.zakenn.recruit.repository.Candidature;
import com.zakenn.recruit.utils.ResponseUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.activiti.engine.impl.identity.Authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    HistoryService historyService;


    public String applyForJob(ApplicationProcessDto applicationsProcessDto, String processName){

        log.info("Start  process : " + processName + " with data : " + applicationsProcessDto);
        Candidature application = applicantService.saveAndGetApplicant(applicationsProcessDto);
        Map<String, Object> vars = Map.of("application", application, "resumeB64", applicationsProcessDto.getResumeAttachment(), "response", ResponseUtils.defaultResponseData);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processName, vars);
        log.info("pending  processInstanceId : " + processInstance.getProcessInstanceId());
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
        log.info("***************  End processID :  " + processInstanceId + " ********************");

    }

    public List<String> getPendingProcessByRecruiter(String processKey, String nameRecruiter) {
        log.info("get history");
        List<String> pendingtasks = new ArrayList<>();
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().list();
        for(int i=0;i< historicProcessInstances.size();i++) {

            List<HistoricTaskInstance> taskList = this.historyService.createHistoricTaskInstanceQuery()
                    .processInstanceId(historicProcessInstances.get(i).getId()).orderByTaskCreateTime().desc().list();
            for (HistoricTaskInstance task : taskList) {
                ProcessDefinition processDefinition = this.repositoryService.getProcessDefinition(task.getProcessDefinitionId());
                pendingtasks.add(task.getId());
            }
        }
        log.info("pendingtasks : " + pendingtasks);
        return pendingtasks;
    }

}
