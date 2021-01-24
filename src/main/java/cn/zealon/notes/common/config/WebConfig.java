package cn.zealon.notes.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private FileConfig fileConfig;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将访问/uploadFile/** 的路由映射到classpath:/static/images/ 目录下
        //classpath代表项目路径 ;file代表绝对路径
        String path = fileConfig.getPath();
        registry.addResourceHandler("/uploadFile/**").addResourceLocations("classpath:/static/images/"
                //,"file:E:\\db\\"
                ,"file:"+path
                ,"file:/opt/notes/tmp/");
    }

}
