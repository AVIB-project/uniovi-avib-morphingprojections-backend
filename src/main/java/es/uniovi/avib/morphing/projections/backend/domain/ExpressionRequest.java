package es.uniovi.avib.morphing.projections.backend.domain;

import lombok.Getter;

@Getter
public class ExpressionRequest {
	private String bucketName;
	private String keyObjectName;
	private String annotationId;
}
