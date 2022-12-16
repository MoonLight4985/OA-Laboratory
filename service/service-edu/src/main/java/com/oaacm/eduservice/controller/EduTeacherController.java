package com.oaacm.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oaacm.commonutils.R;
import com.oaacm.eduservice.entity.EduTeacher;
import com.oaacm.eduservice.entity.vo.TeacherQueryVo;
import com.oaacm.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-25
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
    //注入service
    @Autowired
    private EduTeacherService teacherService;

    //查询讲师所有列表
    @GetMapping("findAll")
    public R findAllTeacher() {
        //调用service的方法查询所有
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    /**
     * Tombstone a teacher
     *
     * @return whether succeed
     */
    @DeleteMapping("{id}")
    public R removeTeacher(@PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag)
            return R.ok();
        else
            return R.error();
    }

    /**
     * Paging query lecturer
     *
     * @param current current page
     * @param limit   the number of records per page
     * @return
     */
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        //Create a Page Object
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //Call method to implement pagination
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> recodes = pageTeacher.getRecords();

        return R.ok().data("total", total).data("rows", recodes);
//        Map map = new HashMap();
//        map.put("total", total);
//        map.put("rows", recodes);
//        return R.ok().data(map);
    }

    /**
     * query in condition
     */
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQueryVo teacherQuery) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询分页
        teacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * insert a teacher into database
     */
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = teacherService.save(eduTeacher);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean update = teacherService.updateById(eduTeacher);
        if (update) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}
