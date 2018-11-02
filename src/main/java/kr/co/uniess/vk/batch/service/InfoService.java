package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.repository.AccommodationInfoMapper;
import kr.co.uniess.vk.batch.repository.CourseInfoMapper;
import kr.co.uniess.vk.batch.repository.DetailInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InfoService {

    @Autowired @Lazy
    private AccommodationInfoMapper accommodationInfoMapper;

    @Autowired @Lazy
    private CourseInfoMapper courseInfoMapper;

    @Autowired @Lazy
    private DetailInfoMapper detailInfoMapper;

    public int insertAccommodationInfoList(List<Map<String, Object>> list) {
        return accommodationInfoMapper.insertList(list);
    }

    public int updateAccommodationInfo(Map<String, Object> item) {
        return accommodationInfoMapper.update(item);
    }

    public int deleteAccommodationInfo(String cotId) {
        return detailInfoMapper.delete(cotId);
    }

    public int insertCourseInfoList(List<Map<String, Object>> list) {
        return courseInfoMapper.insertList(list);
    }

    public int updateCourseInfo(Map<String, Object> item) {
        return courseInfoMapper.update(item);
    }

    public int deleteCourseInfo(String cotId) {
        return detailInfoMapper.delete(cotId);
    }

    public int insertDetailInfoList(List<Map<String, Object>> list) {
        return detailInfoMapper.insertList(list);
    }

    public int updateDetailInfo(Map<String, Object> item) {
        return detailInfoMapper.update(item);
    }

    public int deleteDetailInfo(String cotId) {
        return detailInfoMapper.delete(cotId);
    }
}


