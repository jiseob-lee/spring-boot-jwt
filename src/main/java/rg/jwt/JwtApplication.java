package rg.jwt;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
public class JwtApplication extends SpringBootServletInitializer {
    
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JwtApplication.class);
    }
    
	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

	@Bean
	public static BeanDefinitionRegistryPostProcessor jobRegistryBeanPostProcessorRemover() {
		return registry -> registry.removeBeanDefinition("jobRegistryBeanPostProcessor");
	}
}
