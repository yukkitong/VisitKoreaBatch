package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.component.model.Master;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentMasterMapper {
    int getTotalCount();
    String findOne(String contentId);
    int insert(Master master);
    int update(Master master);
}
