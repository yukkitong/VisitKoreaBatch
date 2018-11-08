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

    public String findOneByCotId(String cotid, String url) {
        return imageMapper.findOneByCotId(cotid, url);
    }

    public List<ImageVO> findAllByCotId(String cotid) {
        return imageMapper.findAllByCotId(cotid);
    }

    public String findOneByContentId(String contentid, String url) {
        return imageMapper.findOneByContentId(contentid, url);
    }

    public List<ImageVO> findAllByContentId(String contentid) {
        return imageMapper.findAllByContentId(contentid);
    }

    public int insert(ImageVO image) {
        if (image.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
        if (image.getImageid() == null) {
            image.createAndSetImageId();
        }
        return imageMapper.insert(image);
    }

    public int update(ImageVO image) {
        if (image.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
        return imageMapper.update(image);
    }
}
