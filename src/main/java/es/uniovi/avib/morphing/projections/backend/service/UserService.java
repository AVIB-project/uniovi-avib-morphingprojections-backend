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
public class UserService {	
	private RestTemplate restTemplate;
	
	private OrganizationConfig organizationConfig;
	
	public Object findAll() {
		log.info("find all users from service");
				
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/users";
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();		
	}
	
	public Object findAllByOrganizationId(String organizationId) {
		log.debug("findAllByOrganizationId: found user by Organization id {} from service", organizationId);
				
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/users/organizations/" + organizationId;
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();		
	}
	
	public Object findTotalUsersByOrganizationId(String organizationId) {
		log.debug("findTotalUsersByOrganizationId: found total users by Organization id {} from service", organizationId);
				
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/users/organizations/" + organizationId + "/total";
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();		
	}
	
	public Object findById(String userId) {
		log.debug("findById: found user with id {} from service", userId);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/users/" + userId;
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public Object findByExternalId(String externalId) {
		log.debug("findByExternalId: found user with external id {} from service", externalId);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/users/" + externalId + "/external";
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public Object findByEmail(String email) {
		log.debug("findById: found user with email {} from service", email);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/users/" + email + "/email";
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public Object save(Object user) {
		log.debug("save user from service");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/users";
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(user ,headers);
		  
		ResponseEntity<Object> responseEntityStr = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public void deleteById(String userId) {
		log.debug("delete user from service");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/users/" + userId;
					  
		restTemplate.exchange(url, HttpMethod.DELETE, null, Object.class);
	}
		
	public Object findCasesByUser(String userId) {
		log.debug("findById: found cases with user id {} from aggregate service", userId);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/users/" + userId + "/cases";
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public void resetPassword(String userId, String password) {
		log.debug("resetPassword: found with user id {} from aggregate service", userId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/users/" + userId + "/resetPassword";
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(password ,headers);
		  
		restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
	}
}
