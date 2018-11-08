package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.repository.DatabaseMasterMapper;
import kr.co.uniess.vk.batch.repository.model.DatabaseMasterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseMasterService {

    @Autowired
    private DatabaseMasterMapper databaseMasterMapper;

    public DatabaseMasterVO findOne(String cotid) {
        return databaseMasterMapper.findOne(cotid);
    }

    public int insert(DatabaseMasterVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
        return databaseMasterMapper.insert(item);
    }

    public int update(DatabaseMasterVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
        return databaseMasterMapper.update(item);
    }
}
