package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.ContentTagsVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentTagsMapper {
    String findOne(String cotid, String tagid);
    int insert(ContentTagsVO item);
}
