package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.DatabaseMasterVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DatabaseMasterMapper {
    DatabaseMasterVO findOne(String cotId);
    int insert(DatabaseMasterVO item);
    int update(DatabaseMasterVO item);
}
