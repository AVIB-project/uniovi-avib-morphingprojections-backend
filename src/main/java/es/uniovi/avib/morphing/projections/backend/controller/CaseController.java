package es.uniovi.avib.morphing.projections.backend.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import es.uniovi.avib.morphing.projections.backend.service.CaseService;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/cases", produces = MediaType.APPLICATION_JSON_VALUE)
public class CaseController {
	private CaseService caseService;
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json")
	public String findAll() {
		log.info("find all cases from controller");
        
        Object result = caseService.findAll();
        
        Gson gson = new Gson();
        String response = gson.toJson(result, List.class);
                
        return response;			
	}
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/{caseId}")	
	public String findById(@PathVariable String caseId) {
		log.info("find by id {} case from controller", caseId);
		
		Object result = caseService.findById(caseId);
													
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
                
        return response;	
	}
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/organizations/{organizationId}/users/{userId}")	
	public String findCasesByOrganizationAndUser(@PathVariable String organizationId, @PathVariable String userId) {
		log.info("findCasesByOrganizationAndUser organizationId {} and userId {} from controller", organizationId, userId);
		
		Object result = caseService.findCasesByOrganizationAndUser(organizationId, userId);
													
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
                
        return response;	
	}
	
	@RequestMapping(method = { RequestMethod.POST }, produces = "application/json")	
	public String save(@RequestBody Object _case) {		
		Object organizationSaved = caseService.save(_case);
			
        Gson gson = new Gson();
        String response = gson.toJson(organizationSaved, Object.class);
        
        return response;			
	}

	@RequestMapping(method = { RequestMethod.DELETE },value = "/{caseId}")	
	public void deleteById(@PathVariable String caseId) {		
		log.debug("deleteById: remove case with caseId: {}", caseId);
			
		caseService.deleteById(caseId);					
	}
}
