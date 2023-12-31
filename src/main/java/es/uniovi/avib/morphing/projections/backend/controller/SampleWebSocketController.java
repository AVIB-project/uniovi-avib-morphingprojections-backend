package es.uniovi.avib.morphing.projections.backend.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import es.uniovi.avib.morphing.projections.backend.domain.SampleRequest;
import es.uniovi.avib.morphing.projections.backend.service.SampleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class SampleWebSocketController {
	private SampleService sampleService;
	
	@MessageMapping("/findAll")
	@SendTo("/topic/samples")
	public List<Object> findAll(SampleRequest index) throws Exception {
    	log.info("Start search documents for the index {}", index.getName());
	    
    	long start = System.currentTimeMillis();  
    	List<Object> samples = sampleService.findAll(index.getName());
        long end = System.currentTimeMillis();
        
        log.info("Elapsed Time: "+ (end - start) / 1000F + " seconds");
        
        return samples;
	}
}
