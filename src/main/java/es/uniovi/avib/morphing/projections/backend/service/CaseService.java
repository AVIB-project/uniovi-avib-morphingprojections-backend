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
public class CaseService {	
	private RestTemplate restTemplate;
	
	private OrganizationConfig organizationConfig;
	
	public Object findAll() {
		log.info("find all cases from service");
				
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/cases";
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();		
	}

	public Object findCasesByOrganizationAndUser(String organizationId, String caseId) {
		log.debug("findById: found case with organizationId {} and caseId {} from service", organizationId, caseId);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/cases/organizations/" + organizationId + "/users/" + caseId;
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public Object findTotalCasesByOrganizationAndUser(String organizationId, String caseId) {
		log.debug("findById: found case with organizationId {} and caseId {} from service", organizationId, caseId);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/cases/organizations/" + organizationId + "/users/" + caseId + "/total";
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public Object findById(String caseId) {
		log.debug("findById: found case with id {} from service", caseId);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/cases/" + caseId;
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public Object save(Object _case) {
		log.debug("save case from service");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/cases";
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(_case ,headers);
		  
		ResponseEntity<Object> responseEntityStr = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public void deleteById(String caseId) {
		log.debug("delete case from service");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/cases/" + caseId;
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(caseId ,headers);
		  
		restTemplate.exchange(url, HttpMethod.DELETE, entity, Object.class);
	}
}
