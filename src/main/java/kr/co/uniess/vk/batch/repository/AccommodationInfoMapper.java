package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.AccommodationInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccommodationInfoMapper {
    int insertList(List<AccommodationInfoVO> list);
    int update(AccommodationInfoVO item);
    int delete(String cotId);
}
