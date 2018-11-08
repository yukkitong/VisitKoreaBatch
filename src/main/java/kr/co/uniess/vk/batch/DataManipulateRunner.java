package kr.co.uniess.vk.batch;

import kr.co.uniess.vk.batch.controller.KTOController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class DataManipulateRunner implements Command<List<Map<String, Object>>> {

    private static final Logger logger = LoggerFactory.getLogger(DataManipulateRunner.class);

    @Autowired
    private KTOController controller;

    @Override
    public void execute(List<Map<String, Object>>... arg) {
        controller.process(Collections.unmodifiableList(arg[0]));
    }
}
