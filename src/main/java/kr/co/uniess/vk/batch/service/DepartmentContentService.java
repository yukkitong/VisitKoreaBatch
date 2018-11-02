package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.repository.DepartmentContentMapper;
import kr.co.uniess.vk.batch.repository.model.DepartmentContentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentContentService {

    @Autowired
    private DepartmentContentMapper departmentContentMapper;


    List<DepartmentContentVO> findAllByCotId(String cotId) {
        return departmentContentMapper.findAllByCotId(cotId);
    }

    public int insert(DepartmentContentVO item) {
        return departmentContentMapper.insert(item);
    }

    int update(DepartmentContentVO item) {
        return departmentContentMapper.update(item);
    }
}
