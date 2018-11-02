package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.ShoppingIntroVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingIntroMapper {
    int insert(ShoppingIntroVO item);
    int update(ShoppingIntroVO item);
    int delete(String cotId);
}
