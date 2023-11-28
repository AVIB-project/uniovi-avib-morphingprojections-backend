package es.uniovi.edv.avispe.data.projection.backend.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import es.uniovi.edv.avispe.data.projection.backend.domain.Index;
import es.uniovi.edv.avispe.data.projection.backend.domain.Patient;
import es.uniovi.edv.avispe.data.projection.backend.service.PatientService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class PatientWebSocketController {
	private PatientService patientService;
	
	@MessageMapping("/findAll")
	@SendTo("/topic/patients")
	public List<Patient> findAll(Index index) throws Exception {
    	log.info("Start search documents for the index {}", index.getName());
	    
    	long start = System.currentTimeMillis();	
        List<Patient> patients = patientService.findAll(index.getName());   
        long end = System.currentTimeMillis();
        
        log.info("Elapsed Time: "+ (end - start) / 1000F + " seconds");
        
        return patients;
	}
}
