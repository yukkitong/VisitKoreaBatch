package kr.co.uniess.vk.batch.repository;

import kr.co.uniess.vk.batch.component.model.Image;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageMapper {
    String findOneByCotId(String cotId, String url);
    List<Image> findAllByCotId(String cotId);
    String findOneByContentId(String contentId, String url);
    List<Image> findAllByContentId(String contentId);
    String insert(Image image);
    int update(Image image);
}
