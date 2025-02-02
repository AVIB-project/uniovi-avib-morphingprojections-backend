package es.uniovi.avib.morphing.projections.backend.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeResponse {
	private String attributeId;
	private String projection;
	List<AttributeValue> values;
}
