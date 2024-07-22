package web.logic.validator;
import java.sql.Time;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
public class JacksonConfig {

	@Bean
	@Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Time.class, new TimeSerializer());
        module.addDeserializer(Time.class, new TimeDeserializer());

        mapper.registerModule(module);
        return mapper;
    }
}
