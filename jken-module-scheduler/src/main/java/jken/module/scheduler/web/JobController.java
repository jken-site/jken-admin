package jken.module.scheduler.web;

import com.google.common.collect.Lists;
import jken.module.scheduler.model.JobModel;
import jken.module.scheduler.model.TriggerModel;
import jken.module.scheduler.service.SchedulerService;
import jken.support.web.BaseController;
import org.quartz.Job;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/job")
public class JobController extends BaseController {

    @Autowired
    private SchedulerService schedulerService;

    /**
     * 显示列表页面
     *
     * @param model
     * @return
     */
    @GetMapping(produces = "text/html")
    public String showList(Model model) {
        return "job/list";
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<JobModel> list() throws SchedulerException {
        return schedulerService.findAllJobs();
    }

    /**
     * 显示添加页面
     *
     * @param entity
     * @param model
     * @return
     */
    @GetMapping(value = "/add", produces = "text/html")
    public String showDetailAdd(JobModel entity, @RequestParam("job_class") Class<? extends Job> jobClass, Model model) {
        if (entity == null) {
            entity = new JobModel();
        }
        entity.setJobClass(jobClass);
        return showDetailEdit(entity, model);
    }

    /**
     * 显示编辑页面
     *
     * @param entity
     * @param model
     * @return
     * @throws SchedulerException
     */
    @GetMapping(value = "/{id}", produces = "text/html")
    public String showDetailEdit(@PathVariable("id") JobModel entity, Model model) {
        model.addAttribute("entity", entity);
        return "job/edit_" + entity.getJobClass().getName();
    }

    /**
     * 添加实体
     *
     * @param entity
     * @param bindingResult
     */
    @PostMapping
    @ResponseBody
    public void create(@ModelAttribute @Valid JobModel entity, BindingResult bindingResult) throws SchedulerException {
        if (schedulerService.existsJob(entity.getName())) {
            throw new RuntimeException("job name exists");
        }
        update(entity, bindingResult);
    }

    /**
     * 更新实体
     *
     * @param entity
     * @param bindingResult
     */
    @PutMapping("/{id}")
    @ResponseBody
    public void update(@ModelAttribute("id") @Valid JobModel entity, BindingResult bindingResult) throws SchedulerException {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("validate error");
        }

        schedulerService.saveJob(entity);
    }

    /**
     * 删除实体
     *
     * @param entity
     * @throws SchedulerException
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable("id") JobModel entity) throws SchedulerException {
        schedulerService.deleteJob(entity);
    }

    /**
     * 批量删除实体
     *
     * @param jobModels
     * @throws SchedulerException
     */
    @DeleteMapping
    @ResponseBody
    public void batchDelete(@RequestParam(value = "ids[]") JobModel[] jobModels) throws SchedulerException {
        if (jobModels != null) {
            schedulerService.batchDeleteJobs(Lists.newArrayList(jobModels));
        }
    }

    /**
     * 执行
     *
     * @param entity
     * @throws SchedulerException
     */
    @PutMapping("/{id}/exec")
    @ResponseBody
    public void exec(@PathVariable("id") JobModel entity) throws SchedulerException {
        schedulerService.execJob(entity);
    }

    @GetMapping(value = "/{id}/triggers", produces = "text/html")
    public String showTriggers(@PathVariable("id") JobModel entity, Model model) {
        model.addAttribute("entity", entity);
        return "job/triggers";
    }

    @GetMapping(value = "/{id}/triggers", produces = "application/json")
    @ResponseBody
    public List<TriggerModel> getTriggers(@PathVariable("id") JobModel entity) throws SchedulerException {
        return schedulerService.findJobTriggers(entity.getName());
    }

}
