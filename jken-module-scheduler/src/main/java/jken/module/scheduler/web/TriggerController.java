package jken.module.scheduler.web;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import jken.module.scheduler.model.JobModel;
import jken.module.scheduler.model.TriggerModel;
import jken.module.scheduler.service.SchedulerService;
import jken.support.web.BaseController;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/trigger")
public class TriggerController extends BaseController {

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
        return "trigger/list";
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<TriggerModel> list() throws SchedulerException {
        return schedulerService.findAllTriggers();
    }

    /**
     * 显示添加页面
     *
     * @param entity
     * @param model
     * @return
     */
    @GetMapping(value = "/add", produces = "text/html")
    public String showDetailAdd(TriggerModel entity, @RequestParam(value = "job", required = false) JobModel jobModel, @RequestParam("type") TriggerModel.Type type, Model model) {
        if (entity == null) {
            entity = new TriggerModel();
        }
        entity.setType(type);
        if (jobModel != null && !Strings.isNullOrEmpty(jobModel.getName())) {
            entity.setJobGroup(jobModel.getGroup());
            entity.setJobName(jobModel.getName());
        }

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
    public String showDetailEdit(@PathVariable("id") TriggerModel entity, Model model) {
        model.addAttribute("entity", entity);
        return "trigger/edit_" + entity.getType();
    }

    /**
     * 添加实体
     *
     * @param entity
     * @param bindingResult
     */
    @PostMapping
    @ResponseBody
    public void create(@ModelAttribute @Valid TriggerModel entity, BindingResult bindingResult) throws SchedulerException {
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
    public void update(@ModelAttribute("id") @Valid TriggerModel entity, BindingResult bindingResult) throws SchedulerException {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("validate error");
        }

        schedulerService.saveTrigger(entity);
    }

    /**
     * 删除实体
     *
     * @param entity
     * @throws SchedulerException
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable("id") TriggerModel entity) throws SchedulerException {
        schedulerService.deleteTrigger(entity);
    }

    /**
     * 批量删除实体
     *
     * @param triggerModels
     * @throws SchedulerException
     */
    @DeleteMapping
    @ResponseBody
    public void batchDelete(@RequestParam(value = "ids[]") TriggerModel[] triggerModels) throws SchedulerException {
        if (triggerModels != null) {
            schedulerService.batchDeleteTriggers(Lists.newArrayList(triggerModels));
        }
    }

}
