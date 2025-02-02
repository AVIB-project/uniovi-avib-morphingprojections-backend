package es.uniovi.avib.morphing.projections.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PocUnioviAvibDataProjectionBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocUnioviAvibDataProjectionBackendApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Bean
	public WebMvcConfigurer corsMappingConfigurer() {
	   return new WebMvcConfigurer() {
	       @Override
	       public void addCorsMappings(CorsRegistry registry) {	           
	           registry
	           		.addMapping("/**")
	           		.allowedMethods("*");
	       }	       
	   };
	}	
}
