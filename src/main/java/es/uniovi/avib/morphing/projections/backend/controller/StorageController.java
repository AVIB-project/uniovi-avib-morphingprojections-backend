package es.uniovi.avib.morphing.projections.backend.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.google.gson.Gson;

import es.uniovi.avib.morphing.projections.backend.service.StorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/resources", produces = MediaType.APPLICATION_JSON_VALUE)
public class StorageController {
	private StorageService resourceService;
	
    @RequestMapping(method = { RequestMethod.POST }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json", value = "/organizations/{organizationId}/projects/{projectId}/cases/{caseId}")
    public String uploadFiles(
    		@PathVariable String organizationId,
    		@PathVariable String projectId,
    		@PathVariable String caseId,
            @RequestPart("file[]") MultipartFile[] files) {
		log.info("upload files from controller");
		
		Object result = resourceService.uploadFiles(organizationId, projectId, caseId, files);
													
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
                
        return response;			
	}
}
