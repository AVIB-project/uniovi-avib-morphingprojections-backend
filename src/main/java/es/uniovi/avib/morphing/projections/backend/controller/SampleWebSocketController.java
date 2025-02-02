package es.uniovi.avib.morphing.projections.backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
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
	@SendToUser("/queue/samples")
	public List<Object> findAll(SampleRequest sampleRequest,  Principal user) throws Exception {
    	log.info("Start search documents for the index {}", sampleRequest.getIndex());
	    
    	long start = System.currentTimeMillis();  
    	List<Object> samples = sampleService.findAll(sampleRequest.getIndex(), user);
        long end = System.currentTimeMillis();
        
        log.info("Elapsed Time: "+ (end - start) / 1000F + " seconds");
        
        return samples;
	}
}
