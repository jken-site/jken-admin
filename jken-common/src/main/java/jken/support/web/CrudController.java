/*
 * Copyright (c) 2020.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2020-02-11T13:49:16.347+08:00
 */

package jken.support.web;

import jken.support.data.jpa.Entity;
import jken.support.service.CrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Arrays;

public class CrudController<T extends Entity<I>, I extends Serializable> extends BaseController implements InitializingBean {

    @Autowired
    private CrudService<T, I> service;

    private String viewDir;

    @Override
    public void afterPropertiesSet() {
        RequestMapping requestMapping = getClass().getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            String path = StringUtils.trimToEmpty(requestMapping.value()[0]);
            setViewDir(StringUtils.removeStart(path, "/"));
        }
    }

    /**
     * 显示列表页面
     *
     * @param model
     * @return
     */
    @GetMapping(produces = "text/html")
    public String showList(Model model) {
        onShowList(model);
        return getViewDir() + "/list";
    }

    /**
     * 显示添加页面
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/add", produces = "text/html")
    public String showDetailAdd(T entity, Model model) {
        if (entity == null) {
            entity = getService().createNew();
        }
        return showDetailEdit(entity, model);
    }

    /**
     * 显示编辑页面
     *
     * @param entity
     * @param model
     * @return
     */
    @GetMapping(value = "/{id}", produces = "text/html")
    public String showDetailEdit(@PathVariable("id") T entity, Model model) {
        model.addAttribute("entity", entity);
        onShowEdit(entity, model);
        return getViewDir() + "/edit";
    }

    //=====================================================
    //   以下是REST操作
    //=====================================================

    /**
     * 添加实体
     *
     * @param entity
     * @param bindingResult
     */
    @PostMapping
    @ResponseBody
    public void create(@ModelAttribute @Valid T entity, BindingResult bindingResult) {
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
    public void update(@ModelAttribute("id") @Valid T entity, BindingResult bindingResult) {
        onValidate(entity, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("validate error");
        }

        onBeforeSave(entity);
        onSave(entity);
        onAfterSave(entity);
    }

    /**
     * 删除实体
     *
     * @param entity
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable("id") T entity) {
        if (onBeforeDelete(entity)) {
            onDelete(entity);
        }
    }

    /**
     * 批量删除实体
     *
     * @param entities
     */
    @DeleteMapping
    @ResponseBody
    public void batchDelete(@RequestParam(value = "ids[]") T[] entities) {
        if (entities != null) {
            onBatchDelete(entities);
        }
    }

    protected void onShowList(Model model) {
    }

    protected void onShowEdit(T entity, Model model) {
    }

    protected void onValidate(T entity, BindingResult bindingResult) {
    }

    protected void onBeforeSave(T entity) {
    }

    protected void onSave(T entity) {
        getService().save(entity);
    }

    protected void onAfterSave(T entity) {
    }

    protected boolean onBeforeDelete(T entity) {
        return true;
    }

    protected void onDelete(T entity) {
        getService().delete(entity);
    }

    protected void onBatchDelete(T[] entities) {
        getService().deleteInBatch(Arrays.asList(entities));
    }

    public CrudService<T, I> getService() {
        return service;
    }

    public String getViewDir() {
        return viewDir;
    }

    public void setViewDir(String viewDir) {
        this.viewDir = viewDir;
    }
}