package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.model.Master;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentMasterMapper {
    String findOne(String contentId);
    String insert(Master master);
    int update(Master master);
}
