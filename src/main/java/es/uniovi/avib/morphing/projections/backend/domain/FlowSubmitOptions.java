package es.uniovi.avib.morphing.projections.backend.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlowSubmitOptions {
	private String resourceName;
	private List<String> parameters;
}
