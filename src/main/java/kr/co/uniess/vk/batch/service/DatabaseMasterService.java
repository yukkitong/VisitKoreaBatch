package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.repository.DatabaseMasterMapper;
import kr.co.uniess.vk.batch.repository.model.DatabaseMasterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseMasterService {

    @Autowired
    private DatabaseMasterMapper databaseMasterMapper;

    public DatabaseMasterVO findOne(String cotId) {
        return databaseMasterMapper.findOne(cotId);
    }

    public int insert(DatabaseMasterVO item) {
        return databaseMasterMapper.insert(item);
    }

    public int update(DatabaseMasterVO item) {
        return databaseMasterMapper.update(item);
    }
}
