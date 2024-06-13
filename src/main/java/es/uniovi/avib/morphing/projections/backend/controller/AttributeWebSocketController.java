package es.uniovi.avib.morphing.projections.backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import es.uniovi.avib.morphing.projections.backend.domain.AttributeName;
import es.uniovi.avib.morphing.projections.backend.domain.AttributeNameRequest;
import es.uniovi.avib.morphing.projections.backend.domain.AttributeResponse;
import es.uniovi.avib.morphing.projections.backend.domain.AttributeValueRequest;
import es.uniovi.avib.morphing.projections.backend.service.AttributeService;

@Slf4j
@AllArgsConstructor
@Controller
public class AttributeWebSocketController {
	private AttributeService attributeService;
	
	@MessageMapping("/findAllAttributeNames")
	@SendToUser("/queue/attribute/names")
	public List<AttributeName> findAllAttributeNames(AttributeNameRequest attributeNameRequest, Principal user) throws Exception {
    	log.info("Start search documents for the index {}", attributeNameRequest.getIndex());
	    
    	long start = System.currentTimeMillis();  
    	List<AttributeName> attributeNames = attributeService.findAllAttributeNames(attributeNameRequest.getIndex(), user);
        long end = System.currentTimeMillis();
        
        log.info("Elapsed Time: " + (end - start) / 1000F + " seconds");
        
        return attributeNames;
	}
	
	@MessageMapping("/findAllAttributeValuesByAttributeId")
	@SendToUser("/queue/attribute/values")
	public AttributeResponse findAllAttributeValuesByAttributeId(AttributeValueRequest attributeValueRequest, Principal user) throws Exception {
    	log.info("Start search documents for the index {}", attributeValueRequest.getIndex());
	    
    	long start = System.currentTimeMillis();  
    	AttributeResponse attributeResponse = attributeService.findAllAttributeValuesByAttributeId(
    			attributeValueRequest.getIndex(), 
    			attributeValueRequest.getAttributeId(),
    			attributeValueRequest.getProjection(),
    			user);
    	
        long end = System.currentTimeMillis();
        
        log.info("Elapsed Time: " + (end - start) / 1000F + " seconds");
        
        return attributeResponse;
	}
}
