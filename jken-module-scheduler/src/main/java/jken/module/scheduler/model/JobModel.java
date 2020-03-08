package jken.module.scheduler.model;

import jken.module.scheduler.support.BooleanMap;
import org.quartz.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class JobModel {
    @NotNull
    private String name;

    private String group;

    @NotNull
    private Class<? extends Job> jobClass;

    private final Map<String, Object> objectMap = new HashMap<>();
    private final BooleanMap booleanMap = new BooleanMap();

    private String description;

    private boolean persistJobDataAfterExecution;
    private boolean concurrentExecutionDisallowed;
    private boolean shouldRecover;

    private boolean executing;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<? extends Job> getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class<? extends Job> jobClass) {
        this.jobClass = jobClass;
    }

    public Map<String, Object> getObjectMap() {
        return objectMap;
    }

    public Map<String, Boolean> getBooleanMap() {
        return booleanMap;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPersistJobDataAfterExecution() {
        return persistJobDataAfterExecution;
    }

    public void setPersistJobDataAfterExecution(boolean persistJobDataAfterExecution) {
        this.persistJobDataAfterExecution = persistJobDataAfterExecution;
    }

    public boolean isConcurrentExecutionDisallowed() {
        return concurrentExecutionDisallowed;
    }

    public void setConcurrentExecutionDisallowed(boolean concurrentExecutionDisallowed) {
        this.concurrentExecutionDisallowed = concurrentExecutionDisallowed;
    }

    public boolean isShouldRecover() {
        return shouldRecover;
    }

    public void setShouldRecover(boolean shouldRecover) {
        this.shouldRecover = shouldRecover;
    }

    public boolean isExecuting() {
        return executing;
    }

    public void setExecuting(boolean executing) {
        this.executing = executing;
    }

    public JobKey jobKey() {
        return JobKey.jobKey(this.name, this.group);
    }

    public static JobModel from(JobDetail jobDetail) {
        JobModel model = new JobModel();
        model.setName(jobDetail.getKey().getName());
        model.setGroup(jobDetail.getKey().getGroup());
        model.setJobClass(jobDetail.getJobClass());
        model.setShouldRecover(jobDetail.requestsRecovery());
        model.setConcurrentExecutionDisallowed(jobDetail.isConcurrentExectionDisallowed());
        model.setPersistJobDataAfterExecution(jobDetail.isPersistJobDataAfterExecution());

        for (Map.Entry<String, Object> entry : jobDetail.getJobDataMap().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Boolean) {
                model.getBooleanMap().put(key, (Boolean) value);
            } else {
                model.getObjectMap().put(key, value);
            }
        }

        model.setDescription(jobDetail.getDescription());
        return model;
    }

    public JobDetail toJobDetail() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(this.objectMap);
        jobDataMap.putAll(this.booleanMap);
        return JobBuilder.newJob(this.jobClass).withIdentity(jobKey()).requestRecovery(this.shouldRecover).storeDurably(true).withDescription(this.description).setJobData(jobDataMap).build();
    }
}
