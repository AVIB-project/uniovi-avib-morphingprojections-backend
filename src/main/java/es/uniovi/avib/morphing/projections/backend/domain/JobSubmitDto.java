package es.uniovi.avib.morphing.projections.backend.domain;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobSubmitDto {
	private String caseId;
	private HashMap<String, String> parameters;
}