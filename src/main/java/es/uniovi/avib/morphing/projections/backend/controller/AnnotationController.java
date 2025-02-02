package es.uniovi.avib.morphing.projections.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/cases/{caseId}")
	public String findAllByCaseId(@PathVariable String caseId) {
		log.info("find all annotations from controller by case id {}", caseId);
        
        Object result = annotationService.findAllByCaseId(caseId);
        
        Gson gson = new Gson();
        String response = gson.toJson(result, List.class);
                
        return response;		
	}
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/cases/{caseId}/available")
	public String findAllAvailableByCaseId(@PathVariable String caseId) {
		log.info("find all annotations from controller by case id {}", caseId);
        
        Object result = annotationService.findAllAvailableByCaseId(caseId);
        
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
	
	@RequestMapping(method = { RequestMethod.POST }, produces = "application/json")	
	public String save(@RequestBody Object user) {		
		Object annotationSaved = annotationService.save(user);
			
        Gson gson = new Gson();
        String response = gson.toJson(annotationSaved);
        
        return response;			
	}
		
    @RequestMapping(method = { RequestMethod.POST }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json", value = "/organizations/{organizationId}/projects/{projectId}/cases/{caseId}")
    public String uploadFiles(
    		@PathVariable String organizationId,
    		@PathVariable String projectId,
    		@PathVariable String caseId,
            @RequestPart("file[]") MultipartFile[] files) {
		log.info("upload files from controller");
		
		Object result = annotationService.uploadFiles(organizationId, projectId, caseId, files);
													
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
        
        return response;			
	}	
    
	@RequestMapping(method = { RequestMethod.DELETE },value = "/{annotationId}")	
	public void deleteById(@PathVariable String annotationId) {		
		log.debug("deleteById: remove annotation with annotationId: {}", annotationId);
			
		annotationService.deleteById(annotationId);					
	}    
}
