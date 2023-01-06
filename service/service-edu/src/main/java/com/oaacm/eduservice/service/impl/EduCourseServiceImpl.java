package com.oaacm.eduservice.service.impl;

import com.oaacm.eduservice.entity.EduCourse;
import com.oaacm.eduservice.entity.EduCourseDescription;
import com.oaacm.eduservice.entity.vo.CourseInfoVo;
import com.oaacm.eduservice.mapper.EduCourseMapper;
import com.oaacm.eduservice.service.EduCourseDescriptionService;
import com.oaacm.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oaacm.servicebase.exceptionhandler.ACMException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author MoonLight
 * @since 2023-01-04
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表中添加课程的基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0) {
            throw new ACMException(20001, "添加课程失败");
        }
        String cid = eduCourse.getId();
        //添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }
}
