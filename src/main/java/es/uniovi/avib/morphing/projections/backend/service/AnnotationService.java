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

import es.uniovi.avib.morphing.projections.backend.configuration.AnnotationConfig;

@Slf4j
@AllArgsConstructor
@Service
public class AnnotationService {	
	private RestTemplate restTemplate;
	
	private AnnotationConfig annotationConfig;
	
	public Object findAll() {
		log.info("find all annotations from service");
				
		String url = "http://" + annotationConfig.getHost() + ":" + annotationConfig.getPort() + "/annotations";
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();		
	}
	
	public Object findById(String annotationId) {
		log.debug("findById: found annotation with id {} from service", annotationId);
		
		String url = "http://" + annotationConfig.getHost() + ":" + annotationConfig.getPort() + "/annotations/" + annotationId;
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public Object save(Object annotation) {
		log.debug("save annotation from service");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + annotationConfig.getHost() + ":" + annotationConfig.getPort() + "/annotations";
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(annotation ,headers);
		  
		ResponseEntity<Object> responseEntityStr = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public void deleteById(String annotationId) {
		log.debug("delete annotation from service");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + annotationConfig.getHost() + ":" + annotationConfig.getPort() + "/annotations";
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(annotationId ,headers);
		  
		restTemplate.exchange(url, HttpMethod.DELETE, entity, Object.class);
	}
}
