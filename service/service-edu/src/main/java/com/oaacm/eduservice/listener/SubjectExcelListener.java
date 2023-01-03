package com.oaacm.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oaacm.eduservice.entity.EduSubject;
import com.oaacm.eduservice.entity.excel.SubjectData;
import com.oaacm.eduservice.service.EduSubjectService;
import com.oaacm.servicebase.exceptionhandler.ACMException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new ACMException(20001, "文件数据为空");
        }
        //一行一行读取
        EduSubject one = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (one == null) {
            one = new EduSubject();
            one.setParentId("0");
            one.setTitle(subjectData.getOneSubjectName());
            subjectService.save(one);
        }
        String pid = one.getId();
        EduSubject two = this.existTwoSubject(subjectService, subjectData.getOneSubjectName(), pid);
        if (two == null) {
            two = new EduSubject();
            two.setParentId(pid);
            two.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(two);
        }
    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", 0);
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }

    //判断2级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
