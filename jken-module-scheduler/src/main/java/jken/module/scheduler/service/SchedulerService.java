package jken.module.scheduler.service;

import com.google.common.base.Strings;
import jken.module.scheduler.model.JobModel;
import jken.module.scheduler.model.TriggerModel;
import jken.security.CorpCodeHolder;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SchedulerService {

    @Autowired
    private Scheduler scheduler;

    public List<JobModel> findAllJobs() throws SchedulerException {
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(CorpCodeHolder.getCurrentCorpCode()));
        List<JobDetail> executingJobs = scheduler.getCurrentlyExecutingJobs().stream().map(JobExecutionContext::getJobDetail).collect(Collectors.toList());

        return jobKeys.stream().map(jobKey -> {
            try {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                JobModel jobModel = JobModel.from(jobDetail);
                jobModel.setExecuting(executingJobs.contains(jobDetail));
                return jobModel;
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    public JobModel findByJobKey(String name) throws SchedulerException {
        JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(name, CorpCodeHolder.getCurrentCorpCode()));
        JobModel jobModel = JobModel.from(jobDetail);
        return jobModel;
    }

    public boolean existsJob(String name) throws SchedulerException {
        return scheduler.checkExists(JobKey.jobKey(name, CorpCodeHolder.getCurrentCorpCode()));
    }

    public void saveJob(JobModel jobModel) throws SchedulerException {
        if (Strings.isNullOrEmpty(jobModel.getGroup())) {
            jobModel.setGroup(CorpCodeHolder.getCurrentCorpCode());
        }
        assertGroup(jobModel.getGroup());
        scheduler.addJob(jobModel.toJobDetail(), true);
    }

    public void deleteJob(JobModel jobModel) throws SchedulerException {
        assertGroup(jobModel.getGroup());
        scheduler.deleteJob(jobModel.jobKey());
    }

    public void batchDeleteJobs(List<JobModel> jobModels) throws SchedulerException {
        scheduler.deleteJobs(jobModels.stream().map(jobModel -> {
            assertGroup(jobModel.getGroup());
            return jobModel.jobKey();
        }).collect(Collectors.toList()));
    }

    public void execJob(JobModel jobModel) throws SchedulerException {
        assertGroup(jobModel.getGroup());
        scheduler.triggerJob(jobModel.jobKey());
    }

    public List<TriggerModel> findJobTriggers(String name) throws SchedulerException {
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(JobKey.jobKey(name, CorpCodeHolder.getCurrentCorpCode()));
        return triggers.stream().map(TriggerModel::from).collect(Collectors.toList());
    }

    public List<TriggerModel> findAllTriggers() throws SchedulerException {
        Set<TriggerKey> triggerKeys = scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(CorpCodeHolder.getCurrentCorpCode()));
        return triggerKeys.stream().map(triggerKey -> {
            try {
                Trigger trigger = scheduler.getTrigger(triggerKey);
                return TriggerModel.from(trigger);
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    public boolean existsTrigger(String name) throws SchedulerException {
        return scheduler.checkExists(TriggerKey.triggerKey(name, CorpCodeHolder.getCurrentCorpCode()));
    }

    public void saveTrigger(TriggerModel triggerModel) throws SchedulerException {
        if (Strings.isNullOrEmpty(triggerModel.getGroup())) {
            triggerModel.setGroup(CorpCodeHolder.getCurrentCorpCode());
        }
        assertGroup(triggerModel.getGroup());
        if (Strings.isNullOrEmpty(triggerModel.getJobGroup())) {
            triggerModel.setJobGroup(CorpCodeHolder.getCurrentCorpCode());
        }
        assertGroup(triggerModel.getJobGroup());

        Trigger trigger = triggerModel.toTrigger();
        if (scheduler.checkExists(trigger.getKey())) {
            scheduler.rescheduleJob(trigger.getKey(), trigger);
        } else {
            scheduler.scheduleJob(trigger);
        }
    }

    public void deleteTrigger(TriggerModel triggerModel) throws SchedulerException {
        assertGroup(triggerModel.getGroup());
        scheduler.unscheduleJob(triggerModel.triggerKey());
    }

    public void batchDeleteTriggers(List<TriggerModel> triggerModels) throws SchedulerException {
        scheduler.unscheduleJobs(triggerModels.stream().map(triggerModel -> {
            assertGroup(triggerModel.getGroup());
            return triggerModel.triggerKey();
        }).collect(Collectors.toList()));
    }

    protected void assertGroup(String group) {
        String corpCode = CorpCodeHolder.getCurrentCorpCode();
        if (!Objects.equals(group, corpCode)) {
            throw new RuntimeException("could not change group");
        }
    }

}
