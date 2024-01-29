package es.uniovi.avib.morphing.projections.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.google.gson.Gson;

import es.uniovi.avib.morphing.projections.backend.service.AnnotationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/annotations", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnnotationController {
	private AnnotationService annotationService;
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json")
	public String findAll() {
		log.info("find all annotations from controller");
        
        Object result = annotationService.findAll();
        
        Gson gson = new Gson();
        String response = gson.toJson(result, List.class);
                
        return response;		
	}
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/{annotationId}")
	public String findById(@PathVariable String annotationId) {
		log.info("find by id {} annotations from controller", annotationId);
		
		Object result = annotationService.findById(annotationId);
													
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
                
        return response;		
	}
}
