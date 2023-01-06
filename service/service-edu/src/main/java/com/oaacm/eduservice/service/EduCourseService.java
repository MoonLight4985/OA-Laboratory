package com.oaacm.eduservice.service;

import com.oaacm.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oaacm.eduservice.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author MoonLight
 * @since 2023-01-04
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);
}
