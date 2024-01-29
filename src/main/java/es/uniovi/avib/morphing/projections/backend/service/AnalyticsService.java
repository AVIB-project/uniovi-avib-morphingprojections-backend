package es.uniovi.avib.morphing.projections.backend.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.uniovi.avib.morphing.projections.backend.configuration.AnalyticsConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class AnalyticsService {	
	private RestTemplate restTemplate;
	
	private AnalyticsConfig analyticsConfig;
	
	public Object executeHistogram(Object data) {			
		log.info("Execute histogram analytics from service");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Object> request = new HttpEntity<Object>(data, headers);
		
		String url = "http://" + analyticsConfig.getHost() + ":" + analyticsConfig.getPort() + "/analytics/histogram";
		
		ResponseEntity<Object> responseEntityStr = restTemplate.postForEntity(url, request, Object.class);
		
		return responseEntityStr.getBody();
	}
}
