package es.uniovi.avib.morphing.projections.backend.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import es.uniovi.avib.morphing.projections.backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	private UserService userService;
	
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json")
	public String findAll() {
		log.info("find all users from controller");
        
        Object result = userService.findAll();
        
        Gson gson = new Gson();
        String response = gson.toJson(result, List.class);
                
        return response;			
	}

	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/{userId}")	
	public String findById(@PathVariable String userId) {
		log.info("find by id {} user from controller", userId);
		
		Object result = userService.findById(userId);
													
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
                
        return response;	
	}
	
	@RequestMapping(method = { RequestMethod.POST }, produces = "application/json")	
	public String save(@RequestBody Object user) {		
		Object userSaved = userService.save(user);
			
        Gson gson = new Gson();
        String response = gson.toJson(userSaved, Object.class);
        
        return response;			
	}

	@RequestMapping(method = { RequestMethod.DELETE },value = "/{userId}")	
	public void deleteById(@PathVariable String userId) {		
		log.debug("deleteById: remove user with userId: {}", userId);
			
		userService.deleteById(userId);					
	}	
		
	@RequestMapping(method = { RequestMethod.GET }, produces = "application/json", value = "/{userId}/cases")	
	public String findCasesByUserAggregate(@PathVariable String userId) {
		log.info("find by id {} cases from controller", userId);
		
		Object result = userService.findCasesByUserAggregate(userId);
													
        Gson gson = new Gson();
        String response = gson.toJson(result, Object.class);
                
        return response;	
	}	
}
