package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.DepartmentContentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentContentMapper {
    List<DepartmentContentVO> findAllByCotId(String cotid);
    String findOne(String cotid, String otdid);
    int insert(DepartmentContentVO item);
    int update(DepartmentContentVO item);
}
