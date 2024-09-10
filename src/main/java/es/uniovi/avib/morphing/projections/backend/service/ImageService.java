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
import es.uniovi.avib.morphing.projections.backend.configuration.JobConfig;
import es.uniovi.avib.morphing.projections.backend.configuration.OrganizationConfig;

@Slf4j
@AllArgsConstructor
@Service
public class ImageService {	
	private RestTemplate restTemplate;
	
	private OrganizationConfig organizationConfig;
	private JobConfig jobConfig;
	
	public Object findAll() {
		log.info("find all images from service");
				
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/images";
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();		
	}
	
	public Object findById(String imageId) {
		log.debug("findById: found image with id {} from service", imageId);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/images/" + imageId;
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public Object findByOrganizationId(String organizationId) {
		log.debug("findById: found image with organization id {} from service", organizationId);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/images/organizations/" + organizationId;
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public Object save(Object image) {
		log.debug("save annotation from service");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/images";
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(image ,headers);
		  
		ResponseEntity<Object> responseEntityStr = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public void deleteById(String imageId) {
		log.debug("delete organization from service");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + jobConfig.getHost() + ":" + jobConfig.getPort() + "/images/" + imageId;
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(imageId, headers);
		  
		restTemplate.exchange(url, HttpMethod.DELETE, entity, Object.class);
	}
}
