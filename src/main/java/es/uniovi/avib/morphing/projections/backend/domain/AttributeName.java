package es.uniovi.avib.morphing.projections.backend.domain;

import org.springframework.data.elasticsearch.annotations.Field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeName {
	@Field("attribute_id")
	private String attributeId;
}
