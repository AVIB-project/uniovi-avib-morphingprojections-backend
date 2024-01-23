package es.uniovi.avib.morphing.projections.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.google.gson.Gson;

import es.uniovi.avib.morphing.projections.backend.service.AnalyticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/analytics", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnalyticsController {
	private AnalyticsService analyticsService;
	
	@PostMapping("/histogram")
	public String executeHistogram(@RequestBody Object data) throws Exception {
		log.info("Execute histogram analytics from controller");
	            
        Object result = analyticsService.executeHistogram(data);
        
        Gson gson = new Gson();
        String response = gson.toJson(result,List.class);
                
        return response;
	}	
}
