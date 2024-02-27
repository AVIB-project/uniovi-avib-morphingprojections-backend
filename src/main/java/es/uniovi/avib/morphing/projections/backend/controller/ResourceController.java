package es.uniovi.avib.morphing.projections.backend.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import es.uniovi.avib.morphing.projections.backend.service.ResourceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/resources", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResourceController {
	private ResourceService resourceService;
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/cases/{caseId}")	
	public String findByCaseId(@PathVariable String caseId) {
		log.info("find by id {} cases from controller", caseId);
		
		Object result = resourceService.findByCaseId(caseId);
													
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
                
        return response;	
	}
	
    @RequestMapping(method = { RequestMethod.POST }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json", value = "/organizations/{organizationId}/projects/{projectId}/cases/{caseId}")
    public String uploadFiles(
    		@PathVariable String organizationId,
    		@PathVariable String projectId,
    		@PathVariable String caseId,
    		@RequestPart("type") String type,
    		@RequestPart("description") String description,
            @RequestPart("file[]") MultipartFile[] files) {
		log.info("upload files from controller");
		
		Object result = resourceService.uploadFiles(organizationId, projectId, caseId, type, description, files);
													
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
                
        return response;			
	}	
}
