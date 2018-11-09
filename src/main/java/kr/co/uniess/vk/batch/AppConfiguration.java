package kr.co.uniess.vk.batch;

import kr.co.uniess.vk.batch.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public ContentMasterService contentMasterService() {
        return new ContentMasterService();
    }

    @Bean
    public DatabaseMasterService databaseMasterService() {
        return new DatabaseMasterService();
    }

    @Bean
    public DepartmentContentService departmentContentService() {
        return new DepartmentContentService();
    }

    @Bean
    public ImageService imageService() {
        return new ImageService();
    }

    @Bean
    public InfoService infoService() {
        return new InfoService();
    }

    @Bean
    public IntroService introService() {
        return new IntroService();
    }

    @Bean
    public ContentTagsService tagsService() {
        return new ContentTagsService();
    }
}
