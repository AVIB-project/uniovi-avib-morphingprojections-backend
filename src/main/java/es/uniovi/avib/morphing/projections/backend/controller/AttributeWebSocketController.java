package es.uniovi.avib.morphing.projections.backend.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import es.uniovi.avib.morphing.projections.backend.domain.AttributeValue;
import es.uniovi.avib.morphing.projections.backend.domain.AttributeName;
import es.uniovi.avib.morphing.projections.backend.domain.AttributeNameRequest;
import es.uniovi.avib.morphing.projections.backend.domain.AttributeValueRequest;
import es.uniovi.avib.morphing.projections.backend.service.AttributeService;

@Slf4j
@AllArgsConstructor
@Controller
public class AttributeWebSocketController {
	private AttributeService attributeService;
	
	@MessageMapping("/findAllAttributeNamesBySample")
	@SendTo("/topic/attribute/names")
	public List<AttributeName> findAllAttributeNamesBySample(AttributeNameRequest attributeNameRequest) throws Exception {
    	log.info("Start search documents for the index {}", attributeNameRequest.getIndex());
	    
    	long start = System.currentTimeMillis();  
    	List<AttributeName> attributeNames = attributeService.findAllAttributeNamesBySample(attributeNameRequest.getIndex(), attributeNameRequest.getSampleId());
        long end = System.currentTimeMillis();
        
        log.info("Elapsed Time: " + (end - start) / 1000F + " seconds");
        
        return attributeNames;
	}
	
	@MessageMapping("/findAllAttributeValuesByName")
	@SendTo("/topic/attribute/values")
	public List<AttributeValue> findAllAttributeValuesByName(AttributeValueRequest attributeRequest) throws Exception {
    	log.info("Start search documents for the index {}", attributeRequest.getIndex());
	    
    	long start = System.currentTimeMillis();  
    	List<AttributeValue> attributes = attributeService.findAllAttributeValuesByName(attributeRequest.getIndex(), attributeRequest.getName());
        long end = System.currentTimeMillis();
        
        log.info("Elapsed Time: " + (end - start) / 1000F + " seconds");
        
        return attributes;
	}
}
