package com.oaacm.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oaacm.eduservice.entity.EduChapter;
import com.oaacm.eduservice.entity.EduVideo;
import com.oaacm.eduservice.entity.chapter.ChapterVo;
import com.oaacm.eduservice.entity.chapter.VideoVo;
import com.oaacm.eduservice.mapper.EduChapterMapper;
import com.oaacm.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oaacm.eduservice.service.EduVideoService;
import com.oaacm.servicebase.exceptionhandler.ACMException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author MoonLight
 * @since 2023-01-04
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);

        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);
        List<ChapterVo> finalList = new ArrayList<>();
        for (EduChapter each : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(each, chapterVo);
//            finalList.add(chapterVo);
            List<VideoVo> videoList = new ArrayList<>();
            for (EduVideo eachViedo : eduVideoList) {
                if (eachViedo.getChapterId().equals(each.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eachViedo, videoVo);
                    videoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoList);
            finalList.add(chapterVo);
        }

        return finalList;
    }

    /**
     * 删除章节
     *
     * @param chapterId
     * @return
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        //查询章节下面是否存在小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_Id", chapterId);
        int count = eduVideoService.count(wrapper);
        //如果章节下面存在小节
        if (count > 0) {
            throw new ACMException(20001, "存在小节，无法删除该章节");
        } else {
            //不存在小节，删除
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }
}
