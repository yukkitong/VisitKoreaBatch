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
        if (image.getImgid() == null) {
            throw new IllegalArgumentException("`IMG_ID` must not be null.");
        }
        ensureCotId(image);
        return imageMapper.insert(image);
    }

    public int update(ImageVO image) {
        ensureCotId(image);
        return imageMapper.update(image);
    }

    private void ensureCotId(ImageVO item) {
        if (item.getCotid() == null) {
            throw new IllegalArgumentException("`COT_ID` must not be null.");
        }
    }
}
