package es.uniovi.avib.morphing.projections.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class AnalyticsService {
	@Autowired
	private RestTemplate restTemplate;
	
	public Object executeHistogram(Object data) {			
		log.info("Execute histogram analytics from service");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Object> request = new HttpEntity<Object>(data, headers);
		
		ResponseEntity<Object> responseEntityStr = restTemplate.postForEntity("http://localhost:5000/analytics/histogram", request, Object.class);
		
		return responseEntityStr.getBody();
	}
}
