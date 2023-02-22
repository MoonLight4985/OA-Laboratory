package com.oaacm.eduservice.service;

import com.oaacm.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oaacm.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author MoonLight
 * @since 2023-01-04
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);
}
