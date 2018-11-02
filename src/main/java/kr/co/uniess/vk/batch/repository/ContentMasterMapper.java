package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.ContentMasterVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentMasterMapper {
    int getTotalCount();
    String findOne(String contentId);
    int insert(ContentMasterVO item);
    int update(ContentMasterVO item);
}
