package es.uniovi.avib.morphing.projections.backend.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.uniovi.avib.morphing.projections.backend.configuration.JobConfig;
import es.uniovi.avib.morphing.projections.backend.domain.JobSubmitDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class JobService {
	private RestTemplate restTemplate;
	
	private JobConfig jobConfig;

	public Object findAllByCaseId(String caseId) {
		log.info("find all jobs with caseId with name {} from service", caseId);
				
		String url = "http://" + jobConfig.getHost() + ":" + jobConfig.getPort() + "/jobs/cases/" + caseId;
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();		
	}
	
	public Object getJobLogs(String jobName) {
		log.debug("getJobLogs: found jobs with name jobName {} from service", jobName);
		
		String url = "http://" + jobConfig.getHost() + ":" + jobConfig.getPort() + "/jobs/" + jobName + "/getJobLogs";
		
		ResponseEntity<Object> responseEntityStr = restTemplate.getForEntity(url, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public Object submitJob(JobSubmitDto jobSubmitDto) throws Exception {
		log.debug("submit job for case with Id {} from service", jobSubmitDto.getCaseId());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + jobConfig.getHost() + ":" + jobConfig.getPort() + "/jobs/submitJob";
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(jobSubmitDto, headers);
		  
		ResponseEntity<Object> responseEntityStr = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
		
		return responseEntityStr.getBody();
	}
	
	public void deleteById(String imageId) {
		log.debug("delete image from service");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String url = "http://" + jobConfig.getHost() + ":" + jobConfig.getPort() + "/" + imageId;
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity entity = new HttpEntity(imageId ,headers);
		  
		restTemplate.exchange(url, HttpMethod.DELETE, entity, Object.class);
	}	
}
