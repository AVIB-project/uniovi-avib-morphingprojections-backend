package es.uniovi.avib.morphing.projections.backend.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.uniovi.avib.morphing.projections.backend.configuration.FlowConfig;
import es.uniovi.avib.morphing.projections.backend.domain.FlowSubmitOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class FlowService {
	private RestTemplate restTemplate;
	
	private FlowConfig flowConfig;
	
	public Object submitFlow(FlowSubmitOptions flowSubmitOptions) throws Exception {
		log.debug("save annotation from service");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + flowConfig.getHost() + ":" + flowConfig.getPort() + "/flows/submitFlow";
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(flowSubmitOptions, headers);
		  
		ResponseEntity<Object> responseEntityStr = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
		
		return responseEntityStr.getBody();
	}
}
