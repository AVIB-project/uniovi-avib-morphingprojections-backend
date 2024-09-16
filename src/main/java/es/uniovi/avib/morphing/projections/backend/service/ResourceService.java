package es.uniovi.avib.morphing.projections.backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import es.uniovi.avib.morphing.projections.backend.configuration.OrganizationConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class ResourceService {
	private RestTemplate restTemplate;
	
	private OrganizationConfig organizationConfig;
	
	public Object findByCaseId(String caseId) {
		log.debug("findByCaseId: found cases with id {} from service", caseId);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/resources/cases/" + caseId;
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public Object uploadResources(String organizationId, String projectId, String caseId, String type, String description, MultipartFile[] files) {
		log.info("update files from service");
				
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/resources"
			+ "/organizations/" + organizationId
			+ "/projects/" + projectId
			+ "/cases/" + caseId;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		Arrays.asList(files).forEach(file -> {
			body.add("type", type);		
			body.add("description", description);		
			body.add("file[]", file.getResource());			    					
		});
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);		
		
		ResponseEntity<Object> responseEntityStr = restTemplate.postForEntity(url, requestEntity, Object.class);
		
		return responseEntityStr.getBody();		
	}
	
 	public Object downloadFileByFilename(String organizationId, String projectId, String caseId, String file) {
		log.info("download file from name {}", file);
		
		// put object into minio
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/resources"
			+ "/organizations/" + organizationId
			+ "/projects/" + projectId
			+ "/cases/" + caseId
			+ "/file/" + file;
				
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv; charset=utf-8");
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
				
		return responseEntity.getBody();
     } 
 	
	public void deleteResource(String organizationId, String projectId, String caseId, String file) {
		log.info("deleteResource file {} from service", file);
		
		String url = "http://" + organizationConfig.getHost() + ":" + organizationConfig.getPort() + "/resources"
			+ "/organizations/" + organizationId
			+ "/projects/" + projectId
			+ "/cases/" + caseId
			+ "/file/" + file;
					
		restTemplate.delete(url);  	
	} 	
}
