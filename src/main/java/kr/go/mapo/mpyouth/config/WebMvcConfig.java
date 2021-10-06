package kr.go.mapo.mpyouth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.dir}")
    private String resourcePath;

    @Value("${file.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("uploadPath : {}, resourcePath : {}", uploadPath, resourcePath);
        registry.addResourceHandler(uploadPath)
                .addResourceLocations("file:///" + resourcePath);
    }
}
