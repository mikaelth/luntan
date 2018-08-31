package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Embedded;
import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import org.apache.log4j.Logger;

import se.uu.ebc.luntan.enums.*;

@Entity
@Table(name = "COURSEINSTANCE", uniqueConstraints= @UniqueConstraint(columnNames={"COURSE_FK", "EXTRA_DESIGNATION"}))
public class CourseInstance  extends Auditable {

    private static Logger log = Logger.getLogger(CourseInstance.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    
    @ManyToOne
    @NotNull
    @JoinColumn(name = "COURSE_FK")
	private Course course;

    @Column(name = "EXTRA_DESIGNATION", length = 255)
	@Enumerated(EnumType.STRING)    
	private CIDesignation extraDesignation;

    @Column(name = "REGSTUDENTS", length = 255)
	private Integer registeredStudents;

    @Column(name = "ASSUMEDSTUDENTS")
	private Integer startRegStudents;

    @OneToOne(mappedBy = "preceedingCI")
	private CourseInstance succeedingCI;

    @OneToOne
    @NotNull
    @JoinColumn(name = "PREDECESSOR_FK")
	private CourseInstance preceedingCI;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "ECONOMY_DOC_FK")
	private EconomyDocument economyDoc;
        
    @Column(name = "NOTE", length = 255)
    private String note;
  
/* 
	@Embedded
	private GrantDistribution grantDistribution;
 */
     
	@ElementCollection
    private Map<Department,Float> grantDistribution;

    @OneToOne
    @NotNull
    @JoinColumn(name = "MODEL_FK")
	private FundingModel fundingModel;


	/* Setters and getters */
 
 	public Course getCourse()
	{
		return this.course;
	}

	public void setCourse(Course course)
	{
		this.course = course;
	}

	public CIDesignation getExtraDesignation()
	{
		return this.extraDesignation;
	}

	public void setExtraDesignation(CIDesignation extraDesignation)
	{
		this.extraDesignation = extraDesignation;
	}

	public Integer getRegisteredStudents()
	{
		return this.registeredStudents;
	}

	public void setRegisteredStudents(Integer registeredStudents)
	{
		this.registeredStudents = registeredStudents;
	}

	public Integer getStartRegStudents()
	{
		return this.startRegStudents;
	}

	public void setStartRegStudents(Integer startRegStudents)
	{
		this.startRegStudents = startRegStudents;
	}


	public CourseInstance getSucceedingCI()
	{
		return this.succeedingCI;
	}

	public void setSucceedingCI(CourseInstance succeedingCI)
	{
		this.succeedingCI = succeedingCI;
	}


	public CourseInstance getPreceedingCI()
	{
		return this.preceedingCI;
	}

	public void setPreceedingCI(CourseInstance preceedingCI)
	{
		this.preceedingCI = preceedingCI;
	}

	public EconomyDocument getEconomyDoc()
	{
		return this.economyDoc;
	}

	public void setEconomyDoc(EconomyDocument economyDoc)
	{
		this.economyDoc = economyDoc;
	}

    public String getNote()
    {
    	return this.note;
    }

    public void setNote(String note)
    {
    	this.note = note;
    }

/* 
	public GrantDistribution getGrantDistribution()
	{
		return this.grantDistribution;
	}

	public void setGrantDistribution(GrantDistribution grantDistribution)
	{
		this.grantDistribution = grantDistribution;
	}
 */

	public Map<Department,Float> getGrantDistribution()
	{
		return this.grantDistribution;
	}

	public void setGrantDistribution(Map<Department,Float> grantDistribution)
	{
		this.grantDistribution = grantDistribution;
	}


 	public FundingModel getFundingModel()
	{
		return this.fundingModel;
	}

	public void setFundingModel(FundingModel fundingModel)
	{
		this.fundingModel = fundingModel;
	}


	/* Business methods */
	
	public String getDesignation () {
		return course.getSeName()+this.extraDesignation;
	}
	
	public Map<Department,Float> computeGrants() { 
		Map<Department,Float> grants = new HashMap<Department,Float>();
		Float grant = computeCIGrant();
		log.debug("Grant for " +getDesignation() +", "+ grant);
		for (Department dept : economyDoc.getAccountedDepts()) {
			grants.put(dept, grant * grantDistribution.get(dept));
		}
	
		return grants;
	
	}
	
	public Map<Department,Float> computeGrantAdjustment() {return null;}
	 
 	public Float computeCIGrant() {
 		return fundingModel.computeFunding(getRegStudentAcnt(),course.getCredits(),economyDoc.getBaseValue());
 	}
 	
	private Integer getRegStudentAcnt() {
		Integer students = 0;
		if (startRegStudents == null || startRegStudents == 0) {
			students = preceedingCI == null ?  0 : preceedingCI.getRegisteredStudents();
		} else {
			students = startRegStudents;
		}
		
		return students; 
 
   }
}
