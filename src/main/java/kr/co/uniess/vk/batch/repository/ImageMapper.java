package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.repository.model.ImageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageMapper {
    String findOneByCotId(String cotid, String url);
    List<ImageVO> findAllByCotId(String cotid);
    String findOneByContentId(String contentid, String url);
    List<ImageVO> findAllByContentId(String contentid);
    int insert(ImageVO image);
    int update(ImageVO image);
}
