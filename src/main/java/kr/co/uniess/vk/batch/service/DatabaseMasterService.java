package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.repository.DatabaseMasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseMasterService {
    @Autowired
    private DatabaseMasterMapper databaseMasterMapper;
}
