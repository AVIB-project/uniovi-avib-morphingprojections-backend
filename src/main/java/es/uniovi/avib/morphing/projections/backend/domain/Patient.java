package es.uniovi.avib.morphing.projections.backend.domain;

import org.springframework.data.elasticsearch.annotations.Field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Patient {
    private String id;
    
    @Field(name="project_id")
	private String projectId;

    @Field(name="sample_id")
	private String sampleId;

    @Field(name="sample_type")
	private String sampleType;
    
    @Field(name="data_type")
	private String dataType;

    @Field(name="cancer_code")
	private String cancerCode;
    
    @Field(name="cancer_definition")
	private String cancerDefinition;

    @Field(name="classification_of_tumor")
	private String classificationOfTumor;

    @Field(name="race")
	private String race;

    @Field(name="gender")
	private String gender;

    @Field(name="ethnicity")
	private String ethnicity;
    
    @Field(name="tumor_stage")
	private String tumorStage;

    @Field(name="morphology")
	private String morphology;
    
    @Field(name="site_of_resection_or_biopsy")
	private String siteOfResectionOrBiopsy;
    
    @Field(name="primary_diagnosis")
	private String primaryDiagnosis;

    @Field(name="metastasis_at_diagnosis")
	private String metastasisAtDiagnosis;

    @Field(name="vital_status")
	private String vitalStatus;    

    @Field(name="prior_malignancy")
	private String priorMalignancy;

    @Field(name="treatment_type")
	private String treatmentType;

    @Field(name="ajcc_pathologic_m")
	private String ajccPathologicM;

    @Field(name="ajcc_pathologic_n")
	private String ajccPathologicN;

    @Field(name="ajcc_pathologic_t")
	private String ajccPathologicT;

    @Field(name="ajcc_pathologic_stage")
	private String ajccPathologicStage;
    
    @Field(name="year_of_diagnosis")
	private int yearOfDiagnosis;

    @Field(name="age_at_diagnosis")
	private int ageAtDiagnosis;

    @Field(name="age_at_index")
	private int ageAtIndex;

    @Field(name="has_metastasis")
	private String has_metastasis;
    
    @Field(name="x_exp_gen")
	private Float xExpGen;

    @Field(name="y_exp_gen")
	private Float yExpGen;

    @Field(name="x_exp_mirna")
	private Float xExpMirna;

    @Field(name="y_exp_mirna")
	private Float yExpMirna;

    @Field(name="x_enc_cancer_code")
	private Float xEncCancerCode;

    @Field(name="y_enc_cancer_code")
	private Float yEncCancerCode;
    
    @Field(name="color")
	private String color;    
}
