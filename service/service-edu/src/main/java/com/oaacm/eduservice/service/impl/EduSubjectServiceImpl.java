package com.oaacm.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oaacm.eduservice.entity.EduSubject;
import com.oaacm.eduservice.entity.excel.SubjectData;
import com.oaacm.eduservice.entity.subject.OneSubject;
import com.oaacm.eduservice.entity.subject.TwoSubject;
import com.oaacm.eduservice.listener.SubjectExcelListener;
import com.oaacm.eduservice.mapper.EduSubjectMapper;
import com.oaacm.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-12-20
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", 0);
        List<EduSubject> oneList = baseMapper.selectList(wrapperOne);
        //查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", 0);
        List<EduSubject> twoList = baseMapper.selectList(wrapperTwo);
        List<OneSubject> finalRes = new ArrayList<>();
        for (EduSubject each : oneList) {
            OneSubject oneSubject = new OneSubject();
            List<TwoSubject> children = new ArrayList<>();

            for (EduSubject two : twoList) {
                TwoSubject t = new TwoSubject();
                if (two.getParentId().equals(each.getId())) {
                    BeanUtils.copyProperties(two, t);
                    children.add(t);
                }
            }

            BeanUtils.copyProperties(each, oneSubject);
            oneSubject.setChildren(children);
            finalRes.add(oneSubject);
        }

        return finalRes;
    }
}
