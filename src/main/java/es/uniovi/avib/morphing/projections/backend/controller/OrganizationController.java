package es.uniovi.avib.morphing.projections.backend.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import es.uniovi.avib.morphing.projections.backend.service.OrganizationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationController {
	private OrganizationService organizationService;
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json")
	public String findAll() {
		log.info("find all annotations from controller");
        
        Object result = organizationService.findAll();
        
        Gson gson = new Gson();
        String response = gson.toJson(result, List.class);
                
        return response;			
	}

	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/{organizationId}")	
	public String findById(@PathVariable String organizationId) {
		log.info("find by id {} organizations from controller", organizationId);
		
		Object result = organizationService.findById(organizationId);
													
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
                
        return response;	
	}
	
	@RequestMapping(method = { RequestMethod.POST }, produces = "application/json")	
	public String save(@RequestBody Object organization) {		
		Object organizationSaved = organizationService.save(organization);
			
        Gson gson = new Gson();
        String response = gson.toJson(organizationSaved, Object.class);
        
        return response;			
	}

	@RequestMapping(method = { RequestMethod.DELETE },value = "/{organizationId}")	
	public void deleteById(@PathVariable String organizationId) {		
		log.debug("deleteById: remove annotation with annotationId: {}", organizationId);
			
		organizationService.deleteById(organizationId);					
	}
}
