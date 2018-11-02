package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.ImageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageMapper {
    String findOneByCotId(String cotId, String url);
    List<ImageVO> findAllByCotId(String cotId);
    String findOneByContentId(String contentId, String url);
    List<ImageVO> findAllByContentId(String contentId);
    String insert(ImageVO image);
    int update(ImageVO image);
}
