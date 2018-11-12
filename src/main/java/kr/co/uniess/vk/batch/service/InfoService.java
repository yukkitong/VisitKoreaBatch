package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.repository.AccommodationInfoMapper;
import kr.co.uniess.vk.batch.repository.CourseInfoMapper;
import kr.co.uniess.vk.batch.repository.DetailInfoMapper;
import kr.co.uniess.vk.batch.repository.model.AccommodationInfoVO;
import kr.co.uniess.vk.batch.repository.model.CourseInfoVO;
import kr.co.uniess.vk.batch.repository.model.DetailInfoVO;
import kr.co.uniess.vk.batch.repository.model.DetailWithTourVO;
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
            if (item.getCotid() == null) {
                throw new IllegalArgumentException("`COT_ID` must not be null.");
            }
        }
        return accommodationInfoMapper.insertList(list);
    }

    public int insertAccommodationInfo(AccommodationInfoVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
        return accommodationInfoMapper.insert(item);
    }

    public int updateAccommodationInfo(AccommodationInfoVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
        return accommodationInfoMapper.update(item);
    }

    public int deleteAccommodationInfo(String cotid) {
        return detailInfoMapper.delete(cotid);
    }

    public int insertCourseInfoList(List<CourseInfoVO> list) {
        for (CourseInfoVO item : list) {
            if (item.getCotid() == null) {
                throw new IllegalArgumentException("`COT_ID` must not be null.");
            }
        }
        return courseInfoMapper.insertList(list);
    }

    public int insertCourseInfo(CourseInfoVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
        return courseInfoMapper.insert(item);
    }

    public int updateCourseInfo(CourseInfoVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
        return courseInfoMapper.update(item);
    }

    public int deleteCourseInfo(String cotid) {
        return detailInfoMapper.delete(cotid);
    }

    public int insertDetailInfoList(List<? extends DetailInfoVO> list) {
        if (list == null) return 0;
        for (DetailInfoVO item : list) {
            if (item.getCotid() == null) {
                throw new IllegalArgumentException("`COT_ID` must not be null.");
            }
        }
        return detailInfoMapper.insertList(list);
    }

    public int insertDetailInfo(DetailInfoVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
        return detailInfoMapper.insert(item);
    }

    public int updateDetailInfo(DetailInfoVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
        return detailInfoMapper.update(item);
    }

    public int deleteDetailInfo(String cotid) {
        return detailInfoMapper.delete(cotid);
    }

    public int deleteDetailInfoWithTour(String cotid) {
        return detailInfoMapper.deleteWithTour(cotid);
    }
}


