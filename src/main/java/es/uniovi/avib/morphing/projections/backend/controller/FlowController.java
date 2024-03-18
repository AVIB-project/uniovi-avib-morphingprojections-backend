package es.uniovi.avib.morphing.projections.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.uniovi.avib.morphing.projections.backend.domain.FlowSubmitOptions;
import es.uniovi.avib.morphing.projections.backend.service.FlowService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value="/flows", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlowController {
	private FlowService flowService;
	
	@RequestMapping(method = { RequestMethod.POST }, produces = "application/json", value = "/submitFlow")	
	public ResponseEntity<Object> submitFlow(@RequestBody FlowSubmitOptions flowSubmitOptions) throws Exception {
		log.debug("submitFlow from controller");
		
		Object flowResult = flowService.submitFlow(flowSubmitOptions);
			
		return new ResponseEntity<Object>(flowResult, HttpStatus.OK);			
	}	
}
