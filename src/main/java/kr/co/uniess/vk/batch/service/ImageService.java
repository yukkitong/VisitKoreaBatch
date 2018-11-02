package kr.co.uniess.vk.batch.service;


import kr.co.uniess.vk.batch.repository.ImageMapper;
import kr.co.uniess.vk.batch.repository.model.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageMapper imageMapper;

    public String findOneByCotId(String cotId, String url) {
        return imageMapper.findOneByCotId(cotId, url);
    }

    public List<ImageVO> findAllByCotId(String cotId) {
        return imageMapper.findAllByCotId(cotId);
    }

    public String findOneByContentId(String contentId, String url) {
        return imageMapper.findOneByContentId(contentId, url);
    }

    public List<ImageVO> findAllByContentId(String contentId) {
        return imageMapper.findAllByContentId(contentId);
    }

    public String insert(ImageVO image) {
        return imageMapper.insert(image);
    }

    public int update(ImageVO image) {
        return imageMapper.update(image);
    }
}
