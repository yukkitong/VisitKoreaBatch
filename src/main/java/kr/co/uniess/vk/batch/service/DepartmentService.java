package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.repository.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
}
