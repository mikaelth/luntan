package se.uu.ebc.luntan.vo;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import se.uu.ebc.luntan.entity.CourseInstance;
import se.uu.ebc.luntan.entity.Examiner;
import se.uu.ebc.luntan.enums.CIDesignation;
import se.uu.ebc.luntan.enums.Department;
import se.uu.ebc.luntan.enums.StudentModelNumberCase;

import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class CourseInstanceCGDVO {


    private Long id;

	private boolean locked;
	private boolean supplement;

	private String ciDesignation;

	private Long courseId;
	private String courseGroup;
	private String courseDesignation;
	private String courseLeader;

	private Long economyDocId;
	private String extraDesignation;
	private String instanceCode;

	private boolean registrationValid;
	private Integer registeredStudents;
	private Integer startRegStudents;
	private Integer modelStudentNumber;

    private String note;
	private boolean firstInstance;

    private Map<String,Float> grantDistribution;
    private Map<String,Float> grantsDistributed;


 	/* Setters and getters */

	public boolean isLocked() {
		return this.locked;
	}


    /* Public methods */


 	/* Constructors */

	public CourseInstanceCGDVO (CourseInstance xe) {

		this.id = xe.getId();

		this.courseId = xe.getCourse().getId();
		this.courseGroup =  xe.getCourse().getCourseGroup().toString();
		this.courseDesignation =  xe.getCourse().getDesignation();
		this.courseLeader =  xe.getCourseLeader();

		this.economyDocId = xe.getEconomyDoc().getId();
		this.locked = xe.getEconomyDoc().isLocked();
		this.supplement = xe.isSupplementary();


		this.extraDesignation = xe.getExtraDesignation().toString();
		this.instanceCode = xe.getInstanceCode();
		this.registrationValid = xe.isRegistrationValid();
		this.registeredStudents = xe.getRegisteredStudents();
		this.startRegStudents = xe.getStartRegStudents();

		this.firstInstance = xe.isFirstInstance();

		this.grantDistribution = xe.getGrantDistribution().entrySet().stream().collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue()));
		this.grantsDistributed = xe.computeGrants().entrySet().stream().collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue()));

		this.modelStudentNumber = xe.getModelStudentNumber();

		this.note = xe.getNote();

		this.ciDesignation = xe.getEconomyDoc().getYear() + "-" + xe.getCourse().getCode()  + "-" + xe.getExtraDesignation();
	}


}
