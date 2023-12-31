package es.uniovi.avib.morphing.projections.backend.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeValue {
	private String sample_id;
	private String attribute;
	private float value;
}
