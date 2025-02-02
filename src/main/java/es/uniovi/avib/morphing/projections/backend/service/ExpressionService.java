package es.uniovi.avib.morphing.projections.backend.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import es.uniovi.avib.morphing.projections.backend.configuration.StorageConfig;
import es.uniovi.avib.morphing.projections.backend.domain.ExpressionRequest;
import es.uniovi.avib.morphing.projections.backend.domain.ProjectionRequest;

@Slf4j
@AllArgsConstructor
@Service
public class ExpressionService {
	private RestTemplate restTemplate;
	
	private StorageConfig storageConfig;

	public Object findAnnotationsName(ProjectionRequest projectionRequest) {
		log.info("find all annotations name from service");
				
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + storageConfig.getHost() + ":" + storageConfig.getPort() + "/expressions/annotations/name";
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(projectionRequest ,headers);
		  
		ResponseEntity<Object> responseEntityStr = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
		
		return responseEntityStr.getBody();	
	}
	
	public Object findAllAnnotationsValueByAnnotationId(ExpressionRequest expressionRequest) {
		log.info("find all annotations value by annotation id from service");
				
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + storageConfig.getHost() + ":" + storageConfig.getPort() + "/expressions/annotations/value";
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(expressionRequest ,headers);
		  
		ResponseEntity<Object> responseEntityStr = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
		
		return responseEntityStr.getBody();	
	}
}
