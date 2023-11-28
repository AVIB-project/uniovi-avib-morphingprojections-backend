package es.uniovi.edv.avispe.data.projection.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Hit {
	long hit;
	long totalHits;
}
