package cn.zealon.notes.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class EmailConfig {
    private String host;
    private String password;
    private String username;

}
