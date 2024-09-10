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

import es.uniovi.avib.morphing.projections.backend.service.ImageService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/images", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {
	private ImageService imageService;
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json")
	public ResponseEntity<List<Object>> findAll() {
		@SuppressWarnings("unchecked")
		List<Object> resources = (List<Object>) imageService.findAll();
					
		log.debug("findAll: found {} images", resources.size());
		
		return new ResponseEntity<List<Object>>(resources, HttpStatus.OK);			
	}
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/{imageId}")	
	public String findById(@PathVariable String imageId) {
		log.info("find by id {} images from controller", imageId);
		
		Object result = imageService.findById(imageId);
													
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
                
        return response;	
	}
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/organizations/{organizationId}")	
	public String findByOrganizationId(@PathVariable String organizationId) {
		log.info("find images by organization Id {} from controller", organizationId);
		
		Object result = imageService.findByOrganizationId(organizationId);
													
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
                
        return response;	
	}
	
	@RequestMapping(method = { RequestMethod.POST }, produces = "application/json")	
	public String save(@RequestBody Object image) {		
		Object imageSaved = imageService.save(image);
			
        Gson gson = new Gson();
        String response = gson.toJson(imageSaved, Object.class);
        
        return response;			
	}	
	
	@RequestMapping(method = { RequestMethod.DELETE },value = "/{imageId}")	
	public void deleteById(@PathVariable String imageId) {		
		log.debug("deleteById: remove image with imageId: {}", imageId);
			
		imageService.deleteById(imageId);					
	}	
}
