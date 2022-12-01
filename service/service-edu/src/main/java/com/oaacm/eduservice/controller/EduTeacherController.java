package com.oaacm.eduservice.controller;


import com.oaacm.eduservice.entity.EduTeacher;
import com.oaacm.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-25
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
    //注入service
    @Autowired
    private EduTeacherService teacherService;

    //1.查询讲师所有列表
    @GetMapping("findAll")
    public List<EduTeacher> findAllTeacher() {
        //调用service的方法查询所有
        List<EduTeacher> list = teacherService.list(null);
        return list;
    }


}
