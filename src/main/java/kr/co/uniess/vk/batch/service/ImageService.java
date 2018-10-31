package kr.co.uniess.vk.batch.service;

import kr.co.uniess.vk.batch.model.Image;
import kr.co.uniess.vk.batch.repository.ImageMapper;
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

    public List<Image> findAllByCotId(String cotId) {
        return imageMapper.findAllByCotId(cotId);
    }

    public String findOneByContentId(String contentId, String url) {
        return imageMapper.findOneByContentId(contentId, url);
    }

    public List<Image> findAllByContentId(String contentId) {
        return imageMapper.findAllByContentId(contentId);
    }

    public String insert(Image image) {
        return imageMapper.insert(image);
    }

    public int update(Image image) {
        return imageMapper.update(image);
    }
}
