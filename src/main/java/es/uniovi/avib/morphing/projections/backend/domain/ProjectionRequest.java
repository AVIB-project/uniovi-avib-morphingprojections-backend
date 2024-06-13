package es.uniovi.avib.morphing.projections.backend.domain;

import lombok.Getter;

@Getter
public class ProjectionRequest {
	private String bucketName;
	private String keyObjectName;
}
