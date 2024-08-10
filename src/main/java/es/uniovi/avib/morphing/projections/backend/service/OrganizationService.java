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

import es.uniovi.avib.morphing.projections.backend.configuration.OrganizationConfig;

@Slf4j
@AllArgsConstructor
@Service
public class OrganizationService {	
	private RestTemplate restTemplate;
	
	private OrganizationConfig organizationConfig;
	
	public Object findAll() {
		log.info("find all organizations from service");
				
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/organizations";
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();		
	}
	
	public Object findById(String organizationId) {
		log.debug("findById: found organization with id {} from service", organizationId);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/organizations/" + organizationId;
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public Object save(Object organization) {
		log.debug("save annotation from service");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/organizations";
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(organization ,headers);
		  
		ResponseEntity<Object> responseEntityStr = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public void deleteById(String organizationId) {
		log.debug("delete organization from service");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/organizations";
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(organizationId ,headers);
		  
		restTemplate.exchange(url, HttpMethod.DELETE, entity, Object.class);
	}
}
