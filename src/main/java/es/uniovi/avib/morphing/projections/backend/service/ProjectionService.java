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
import es.uniovi.avib.morphing.projections.backend.domain.ProjectionRequest;

@Slf4j
@AllArgsConstructor
@Service
public class ProjectionService {
	private RestTemplate restTemplate;
	
	private StorageConfig storageConfig;
		
	public Object findPrimalProjection(ProjectionRequest projectionRequest) {
		log.info("find primal projection by annotations from service");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + storageConfig.getHost() + ":" + storageConfig.getPort() + "/projections/primal";
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(projectionRequest ,headers);
		  
		ResponseEntity<Object> responseEntityStr = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
		
		return responseEntityStr.getBody();	
	}
	
	public Object findDualProjection(ProjectionRequest projectionRequest) {
		log.info("find dual projection by annotations from service");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + storageConfig.getHost() + ":" + storageConfig.getPort() + "/projections/dual";
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(projectionRequest ,headers);
		  
		ResponseEntity<Object> responseEntityStr = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
		
		return responseEntityStr.getBody();	
	}	
}
