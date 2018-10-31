package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.repository.InfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InfoService {

    @Autowired
    private InfoMapper infoMapper;

    public int insertAccommodationInfoList(List<Map<String, Object>> list) {
        return infoMapper.insertAccommodationInfoList(list);
    }

    public int updateAccommodationInfo(Map<String, Object> item) {
        return infoMapper.updateAccommodationInfo(item);
    }

    public int insertCourseInfoList(List<Map<String, Object>> list) {
        return infoMapper.insertCourseInfoList(list);
    }

    public int updateCourseInfoList(Map<String, Object> item) {
        return infoMapper.updateCourseInfo(item);
    }

    public int insertDetailInfoList(List<Map<String, Object>> list) {
        return infoMapper.insertDetailInfoList(list);
    }

    public int updateDetailInfo(Map<String, Object> item) {
        return infoMapper.updateDetailInfo(item);
    }
}


