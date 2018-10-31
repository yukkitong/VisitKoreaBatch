package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.repository.TagsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagsService {
    @Autowired
    private TagsMapper tagsMapper;
}
