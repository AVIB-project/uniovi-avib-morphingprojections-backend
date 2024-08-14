package es.uniovi.avib.morphing.projections.backend.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import es.uniovi.avib.morphing.projections.backend.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/projects", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {
	private ProjectService projectService;
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json")
	public String findAll() {
		log.info("find all projects from controller");
        
        Object result = projectService.findAll();
        
        Gson gson = new Gson();
        String response = gson.toJson(result, List.class);
                
        return response;			
	}

	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/organizations/{organizationId}")	
	public String findByOrganizationId(@PathVariable String organizationId) {
		log.info("findByOrganizationId by id {} projects from controller", organizationId);
		
		Object result = projectService.findByOrganizationId(organizationId);
													
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
                
        return response;	
	}
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/{organizationId}")	
	public String findById(@PathVariable String projectId) {
		log.info("find by id {} project from controller", projectId);
		
		Object result = projectService.findById(projectId);
													
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
                
        return response;	
	}
	
	@RequestMapping(method = { RequestMethod.POST }, produces = "application/json")	
	public String save(@RequestBody Object organization) {		
		Object organizationSaved = projectService.save(organization);
			
        Gson gson = new Gson();
        String response = gson.toJson(organizationSaved, Object.class);
        
        return response;			
	}

	@RequestMapping(method = { RequestMethod.DELETE },value = "/{organizationId}")	
	public void deleteById(@PathVariable String organizationId) {		
		log.debug("deleteById: remove annotation with annotationId: {}", organizationId);
			
		projectService.deleteById(organizationId);					
	}
}
