package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.repository.AccommodationInfoMapper;
import kr.co.uniess.vk.batch.repository.CourseInfoMapper;
import kr.co.uniess.vk.batch.repository.DetailInfoMapper;
import kr.co.uniess.vk.batch.repository.model.AccommodationInfoVO;
import kr.co.uniess.vk.batch.repository.model.CourseInfoVO;
import kr.co.uniess.vk.batch.repository.model.DetailInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InfoService {

    @Autowired @Lazy
    private AccommodationInfoMapper accommodationInfoMapper;

    @Autowired @Lazy
    private CourseInfoMapper courseInfoMapper;

    @Autowired @Lazy
    private DetailInfoMapper detailInfoMapper;

    public int insertAccommodationInfoList(List<AccommodationInfoVO> list) {
        return accommodationInfoMapper.insertList(list);
    }

    public int updateAccommodationInfo(AccommodationInfoVO item) {
        return accommodationInfoMapper.update(item);
    }

    public int deleteAccommodationInfo(String cotId) {
        return detailInfoMapper.delete(cotId);
    }

    public int insertCourseInfoList(List<CourseInfoVO> list) {
        return courseInfoMapper.insertList(list);
    }

    public int updateCourseInfo(CourseInfoVO item) {
        return courseInfoMapper.update(item);
    }

    public int deleteCourseInfo(String cotId) {
        return detailInfoMapper.delete(cotId);
    }

    public int insertDetailInfoList(List<DetailInfoVO> list) {
        return detailInfoMapper.insertList(list);
    }

    public int updateDetailInfo(DetailInfoVO item) {
        return detailInfoMapper.update(item);
    }

    public int deleteDetailInfo(String cotId) {
        return detailInfoMapper.delete(cotId);
    }
}


