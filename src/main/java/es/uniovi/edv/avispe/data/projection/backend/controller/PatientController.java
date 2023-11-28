package es.uniovi.edv.avispe.data.projection.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.uniovi.edv.avispe.data.projection.backend.domain.Patient;
import es.uniovi.edv.avispe.data.projection.backend.service.PatientService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientController {
	private PatientService patientService;
		
    @GetMapping("/findAll")
    @ResponseBody
    public List<Patient> findAll(@RequestParam(name = "index", required = true) String indexName) {    	
    	log.info("Start search documents for the index {}", indexName);
    	    
    	long start = System.currentTimeMillis();	
        List<Patient> patients = patientService.findAll(indexName);   
        long end = System.currentTimeMillis();
        
        log.info("Elapsed Time: "+ (end - start) / 1000F + " seconds");
        
        return patients;
    }
}
