package rg.jwt.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import rg.jwt.util.CustomAccessDeniedHandler;
import rg.jwt.util.CustomAuthenticationEntryPoint;

@Configuration
//@EnableWebMvc
public class Config {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
		return new CustomAuthenticationEntryPoint();
	}

	/*
	@Bean
	@Primary
	public ObjectMapper objectMapper() {
	    JavaTimeModule module = new JavaTimeModule();
	    //module.addSerializer(LOCAL_DATETIME_SERIALIZER);
	    return new ObjectMapper()
	      .setSerializationInclusion(JsonInclude.Include.NON_NULL)
	      .registerModule(module);
	}
	
	@Bean
	public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
	    return new Jackson2ObjectMapperBuilder().serializers(LOCAL_DATETIME_SERIALIZER)
	      .serializationInclusion(JsonInclude.Include.NON_NULL);
	}
	*/
}
