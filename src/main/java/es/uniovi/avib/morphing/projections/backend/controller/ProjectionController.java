package es.uniovi.avib.morphing.projections.backend.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import es.uniovi.avib.morphing.projections.backend.domain.ProjectionRequest;
import es.uniovi.avib.morphing.projections.backend.service.ProjectionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/projections", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectionController {
	private ProjectionService projectionService;
	
	@RequestMapping(value = "/primal", method = { RequestMethod.POST }, produces = "application/json")
	public String findPrimalProjection(@RequestBody ProjectionRequest projectionRequest) {
		log.info("find all expressions by annotation from controller");
		
        Object result = projectionService.findPrimalProjection(projectionRequest);
        
        Gson gson = new Gson();
        String response = gson.toJson(result, List.class);
                
        return response;			
	}	
	
	@RequestMapping(value = "/dual", method = { RequestMethod.POST }, produces = "application/json")
	public String findDualProjection(@RequestBody ProjectionRequest projectionRequest) {
		log.info("find all expressions by annotation from controller");
		
        Object result = projectionService.findPrimalProjection(projectionRequest);
        
        Gson gson = new Gson();
        String response = gson.toJson(result, List.class);
                
        return response;			
	}	
}
