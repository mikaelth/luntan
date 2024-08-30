package se.uu.ebc.luntan.vo;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import se.uu.ebc.luntan.entity.CourseInstance;
import se.uu.ebc.luntan.entity.IndividualYearlyCourse;
import se.uu.ebc.luntan.entity.Examiner;
import se.uu.ebc.luntan.enums.CIDesignation;
import se.uu.ebc.luntan.enums.Department;
import se.uu.ebc.luntan.enums.StudentModelNumberCase;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class CourseInstanceVO {

    
//    private static Logger logger = Logger.getLogger(CourseInstanceVO.class.getName());
	 
    private Long id;

	private boolean locked; 
	private boolean supplement; 
	
	private String ciDesignation;
	
	private Long courseId;
	private String courseGroup;
	private String courseDesignation;
	private String courseLeader;
	
	private Long preceedingCIId;
	private Long economyDocId;
	private Long balancedEconomyDocId;
	private Long fundingModelId;

	private CIDesignation extraDesignation;
	private String instanceCode;

	private boolean registrationValid;
	private Integer registeredStudents;
	private Integer startRegStudents;
	private Integer modelStudentNumber;
	private StudentModelNumberCase modelCase;
	
    private String note;
	private boolean balanceRequest = false;
	private boolean firstInstance;
	private boolean bookendOnly;
	
//	private Set<String> examiners;
//	private List<Examiner> examiners;

    private Map<Department,Float> grantDistribution;
	
	private boolean individualYearlyCourse;
	
 	/* Setters and getters */

	public boolean isLocked() {
		return this.locked;
	}
 	   
    
    /* Public methods */

/* 
	public Float getIBG() {
		return this.grantDistribution.get(Department.IBG);
	}
	public Float getICM() {
		return this.grantDistribution.get(Department.ICM);
	} 
	public Float getIEG() {
		return this.grantDistribution.get(Department.IEG);
	}
	public Float getIOB() {
		return this.grantDistribution.get(Department.IOB);
	}
 */

	
 	/* Constructors */

	public CourseInstanceVO (CourseInstance xe) {

		log.debug("CourseInstance constructor: "+ xe);

		this.id = xe.getId();
		
		this.courseId = xe.getCourse().getId();
		this.courseGroup =  xe.getCourse().getCourseGroup().toString();
		this.courseDesignation =  xe.getCourse().getDesignation();
		this.courseLeader =  xe.getCourseLeader();
		
		this.preceedingCIId = xe.getPreceedingCI() == null ? null : xe.getPreceedingCI().getId();
		this.economyDocId = xe.getEconomyDoc().getId();
		this.locked = xe.getEconomyDoc().isLocked();
		this.supplement = xe.isSupplementary();
		
		this.balancedEconomyDocId = xe.getBalancedEconomyDoc() == null ? null : xe.getBalancedEconomyDoc().getId();
		this.fundingModelId = xe.getFundingModel().getId();
		
		this.extraDesignation = xe.getExtraDesignation();
		this.instanceCode = xe.getInstanceCode();
		this.registrationValid = xe.isRegistrationValid();
		this.registeredStudents = xe.getRegisteredStudents();
		this.startRegStudents = xe.getStartRegStudents();
		
		this.balanceRequest = xe.isBalanceRequest();
		this.firstInstance = xe.isFirstInstance();		
		this.bookendOnly = xe.isBookendOnly();		
		
		this.grantDistribution = xe.getGrantDistribution();
		this.modelStudentNumber = xe.getModelStudentNumber();
		this.modelCase = xe.getModelCase();
		
		this.note = xe.getNote();	

		this.ciDesignation = xe.getEconomyDoc().getYear() + "-" + xe.getCourse().getCode()  + "-" + xe.getExtraDesignation();
		
		this.individualYearlyCourse = xe instanceof IndividualYearlyCourse;

		log.debug("CourseInstance end of constructor");

	}
	
//	public CourseInstanceVO() {}

}
