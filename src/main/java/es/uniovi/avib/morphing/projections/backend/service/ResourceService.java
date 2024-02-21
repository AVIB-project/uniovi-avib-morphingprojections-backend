package es.uniovi.avib.morphing.projections.backend.service;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import es.uniovi.avib.morphing.projections.backend.configuration.ResourceConfig;

@Slf4j
@AllArgsConstructor
@Service
public class ResourceService {	
	private RestTemplate restTemplate;
	
	private ResourceConfig resourceConfig;
	
	public Object uploadFiles(String organizationId, String projectId, String caseId, MultipartFile[] files) {
		log.info("update files from service");
				
		String url = "http://" + resourceConfig.getHost() + ":" + resourceConfig.getPort() + "/resources"
			+ "/organizations/" + organizationId
			+ "/projects/" + projectId
			+ "/cases/" + caseId;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		Arrays.asList(files).forEach(file -> {			
			body.add("file[]", file.getResource());			    					
		});
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);		
		
		ResponseEntity<Object> responseEntityStr = restTemplate.postForEntity(url, requestEntity, Object.class);
		
		return responseEntityStr.getBody();		
	}	
}
