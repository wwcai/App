package cool.cxc.app.common.errdefine;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Data
@Configuration
//@PropertySource(value = "classpath:message.properties")
@ConfigurationProperties(prefix = "error")
@EnableConfigurationProperties(ErrMessage.class)
public class ErrMessage {

    private Map<String, String> messages;
    public String messageByErr(ErrCode errCode) {

        String name = errCode.name().toLowerCase();
        return messages.getOrDefault(name, "Unknown");
    }
}
