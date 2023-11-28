package es.uniovi.edv.avispe.data.projection.backend.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Patient {
    private String id;   
	private Float x;   
	private Float y;
	private Float e_x;
	private Float e_y;
	private String code;
	private String description;
	private String type;
	private String group;
}
