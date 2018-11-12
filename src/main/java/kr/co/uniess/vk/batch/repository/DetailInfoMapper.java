package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.DetailInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DetailInfoMapper {
    int insertList(List<? extends DetailInfoVO> list);
    int insert(DetailInfoVO item);
    int update(DetailInfoVO item);
    int delete(String cotid);
    int deleteWithTour(String cotid);
}
