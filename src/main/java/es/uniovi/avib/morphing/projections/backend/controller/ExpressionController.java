package es.uniovi.avib.morphing.projections.backend.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import es.uniovi.avib.morphing.projections.backend.domain.ExpressionRequest;
import es.uniovi.avib.morphing.projections.backend.service.ExpressionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/expressions", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpressionController {
	private ExpressionService expressionService;
	
	@RequestMapping(value = "/annotation", method = { RequestMethod.POST }, produces = "application/json")
	public String findAllExpressionsByAnnotation(@RequestBody ExpressionRequest expressionRequest) {
		log.info("find all expressions by annotation from controller");
		
        Object result = expressionService.findAllExpressionsByAnnotation(expressionRequest);
        
        Gson gson = new Gson();
        String response = gson.toJson(result, List.class);
                
        return response;			
	}	
}
