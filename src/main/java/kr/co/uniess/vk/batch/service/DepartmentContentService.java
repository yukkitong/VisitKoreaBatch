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


    List<DepartmentContentVO> findAllByCotId(String cotid) {
        return departmentContentMapper.findAllByCotId(cotid);
    }

    public int insert(DepartmentContentVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
        return departmentContentMapper.insert(item);
    }

    int update(DepartmentContentVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
        return departmentContentMapper.update(item);
    }
}
