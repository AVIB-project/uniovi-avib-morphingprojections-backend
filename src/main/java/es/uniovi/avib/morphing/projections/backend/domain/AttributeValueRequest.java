package es.uniovi.avib.morphing.projections.backend.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeValueRequest {
	String index;
	String attributeId;
	String projection;
}
