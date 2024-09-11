package es.uniovi.avib.morphing.projections.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import es.uniovi.avib.morphing.projections.backend.domain.JobSubmitDto;
import es.uniovi.avib.morphing.projections.backend.service.JobService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/jobs", produces = MediaType.APPLICATION_JSON_VALUE)
public class JobController {
	private JobService jobService;
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/cases/{caseId}")	
	public String findAllByCaseId(@PathVariable String caseId) {
		log.info("find all jobs from controller");
        
        Object result = jobService.findAllByCaseId(caseId);
        
        Gson gson = new Gson();
        String response = gson.toJson(result, List.class);
                
        return response;			
	}
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/cases/{caseId}/total")	
	public String findTotalJobsByCaseId(@PathVariable String caseId) {
		log.info("find all jobs from controller");
        
        Object response = jobService.findTotalJobsByCaseId(caseId);
           
        return response.toString();			
	}
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/{jobName}/getJobLogs")	
	public String getJobLogs(@PathVariable String jobName) {
		log.info("find all jobs from controller");
        
        Object result = jobService.getJobLogs(jobName);
        
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
                
        return response;			
	}
	
	@RequestMapping(method = { RequestMethod.POST }, produces = "application/json", value = "/submitJob")	
	public ResponseEntity<Object> submitJob(@RequestBody JobSubmitDto jobSubmitDto) throws Exception {
		log.debug("submitJob from controller");
		
		Object flowResult = jobService.submitJob(jobSubmitDto);
			
		return new ResponseEntity<Object>(flowResult, HttpStatus.OK);			
	}	
	
	@RequestMapping(method = { RequestMethod.DELETE },value = "/{imageId}")	
	public void deleteById(@PathVariable String imageId) {		
		log.debug("deleteById: remove image with imageId: {}", imageId);
			
		jobService.deleteById(imageId);					
	}	
}
