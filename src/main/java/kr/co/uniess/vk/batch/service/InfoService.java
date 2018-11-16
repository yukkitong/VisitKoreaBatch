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
        for (AccommodationInfoVO item : list) {
            ensureCotId(item);
        }
        return accommodationInfoMapper.insertList(list);
    }

    public int insertAccommodationInfo(AccommodationInfoVO item) {
        ensureCotId(item);
        return accommodationInfoMapper.insert(item);
    }

    public int updateAccommodationInfo(AccommodationInfoVO item) {
        ensureCotId(item);
        return accommodationInfoMapper.update(item);
    }

    public int deleteAccommodationInfo(String cotid) {
        return accommodationInfoMapper.delete(cotid);
    }

    public int insertCourseInfoList(List<CourseInfoVO> list) {
        for (CourseInfoVO item : list) {
            ensureCotId(item);
        }
        return courseInfoMapper.insertList(list);
    }

    public int insertCourseInfo(CourseInfoVO item) {
        ensureCotId(item);
        return courseInfoMapper.insert(item);
    }

    public int updateCourseInfo(CourseInfoVO item) {
        ensureCotId(item);
        return courseInfoMapper.update(item);
    }

    public int deleteCourseInfo(String cotid) {
        return courseInfoMapper.delete(cotid);
    }

    public int insertDetailInfoList(List<? extends DetailInfoVO> list) {
        if (list == null) return 0;
        for (DetailInfoVO item : list) {
            ensureCotId(item);
        }
        return detailInfoMapper.insertList(list);
    }

    public int insertDetailInfo(DetailInfoVO item) {
        ensureCotId(item);
        return detailInfoMapper.insert(item);
    }

    public int updateDetailInfo(DetailInfoVO item) {
        ensureCotId(item);
        return detailInfoMapper.update(item);
    }

    public int deleteDetailInfo(String cotid) {
        return detailInfoMapper.delete(cotid);
    }

    public int deleteDetailInfoWithTour(String cotid) {
        return detailInfoMapper.deleteWithTour(cotid);
    }

    private void ensureCotId(DetailInfoVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
    }

    private void ensureCotId(CourseInfoVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
    }

    private void ensureCotId(AccommodationInfoVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
    }
}


