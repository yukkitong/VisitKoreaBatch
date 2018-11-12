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


    public List<DepartmentContentVO> findAllByCotId(String cotid) {
        return departmentContentMapper.findAllByCotId(cotid);
    }

    public String findOne(String cotid, String otdid) {
        return departmentContentMapper.findOne(cotid, otdid);
    }

    public int insert(DepartmentContentVO item) {
        ensureCotId(item);
        return departmentContentMapper.insert(item);
    }

    public int update(DepartmentContentVO item) {
        ensureCotId(item);
        return departmentContentMapper.update(item);
    }

    private void ensureCotId(DepartmentContentVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
    }
}
