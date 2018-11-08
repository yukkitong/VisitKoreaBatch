package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.TagsVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagsMapper {
    int insert(TagsVO item);
    int update(TagsVO item);
    int delete(String tagid);
}
