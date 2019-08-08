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
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import lombok.extern.slf4j.Slf4j;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import se.uu.ebc.luntan.enums.*;
import se.uu.ebc.luntan.aux.GrantMaps;

@Slf4j
//@Data
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COURSEINSTANCE", uniqueConstraints= @UniqueConstraint(columnNames={"COURSE_FK", "EXTRA_DESIGNATION","ECONOMY_DOC_FK"}))
public class CourseInstance  extends Auditable implements Comparable<CourseInstance> {

	private static final Integer DEFAULT_REG_STUDENT_NUMBER = 15;
//    private static Logger log = Logger.getLogger(CourseInstance.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
/* 
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
 */

    
    @ManyToOne
    @NotNull
    @JoinColumn(name = "COURSE_FK")
	private Course course;

    @Column(name = "EXTRA_DESIGNATION", length = 255)
	@Enumerated(EnumType.STRING)    
	private CIDesignation extraDesignation;

    @Column(name = "FIRSTINSTANCE")
	private boolean firstInstance;

    @Column(name = "REGSTUDENTS", length = 255)
	private Integer registeredStudents;

    @Column(name = "ASSUMEDSTUDENTS")
	private Integer startRegStudents;

    @Column(name = "LOCKEDSTUDENTNUMBER", length = 255)
	private Integer lRegStud;

    @Column(name = "UNLOCKEDSTUDENTNUMBER")
	private Integer uRegStud;

    @OneToOne
    @JoinColumn(name = "PREDECESSOR_FK")
	private CourseInstance preceedingCI;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "ECONOMY_DOC_FK")
	private EconomyDocument economyDoc;
        
    @Column(name = "NOTE", length = 255)
    private String note;
 
    @Column(name = "BALANCE")
    private boolean balanceRequest = false;

    @ManyToOne
    @JoinColumn(name = "BALANCED_ECON_DOC_FK")
	private EconomyDocument balancedEconomyDoc;
 	 
     
	@ElementCollection
	@MapKeyEnumerated(EnumType.STRING)    
    private Map<Department,Float> grantDistribution;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "MODEL_FK")
	private FundingModel fundingModel;


	/* Setters and getters */
 
/* 
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
 */
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
/* 

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


	public Integer getLRegStud()
	{
		return this.lRegStud;
	}

	public void setLRegStud(Integer lRegStud)
	{
		this.lRegStud = lRegStud;
	}


	public Integer getURegStud()
	{
		return this.uRegStud;
	}

	public void setURegStud(Integer uRegStud)
	{
		this.uRegStud = uRegStud;
	}

	public void setBalanceRequest (boolean balanceRequest) {
		this.balanceRequest = balanceRequest;
	}

	public boolean isBalanceRequest() {
		return this.balanceRequest;
	}
	

	public EconomyDocument getBalancedEconomyDoc()
	{
		return this.balancedEconomyDoc;
	}

	public void setBalancedEconomyDoc(EconomyDocument balancedEconomyDoc)
	{
		this.balancedEconomyDoc = balancedEconomyDoc;
	}

	public boolean getFirstInstance()
	{
		return this.firstInstance;
	}
	public boolean isFirstInstance()
	{
		return this.firstInstance;
	}

	public void setFirstInstance(boolean firstInstance)
	{
		this.firstInstance = firstInstance;
	}

 */

	/* Constructors */
	
//	public CourseInstance() {}
	
	/* Business methods */
	
	public String getDesignation() {
		return course.getDesignation()+this.extraDesignation;
	}
	
	private Map<Department,Float> computeGrantDist(Float grant) { 
		Map<Department,Float> grants = new HashMap<Department,Float>();
		Float implicitGrant = 0.0f;
		Department implicitDept = null;

		log.debug("Grant for " +getDesignation() +", "+ grant);
 
		for (Department dept : economyDoc.getAccountedDepts()) {
			if (dept.isImplicit()) {
				implicitDept = dept;
				implicitGrant += 1.0f;		
			} else if (grantDistribution.containsKey(dept)) {
				grants.put(dept, grant * grantDistribution.get(dept));
				implicitGrant -= grantDistribution.get(dept);
			} else {
				grants.put(dept, 0.0f);			
			}			
		}

		if (implicitDept != null) {
			grants.put(implicitDept, grant * implicitGrant);
		}
	
		return grants;
	
	}

	private Map<Department,Float> computeHSTDist(Float hst) { 
		Map<Department,Float> hsts = new HashMap<Department,Float>();
		Float implicitHST = 0.0f;
		Department implicitDept = null;

		log.debug("HST for " +getDesignation() +", "+ hst);
 
		for (Department dept : economyDoc.getAccountedDepts()) {
			if (dept.isImplicit()) {
				implicitDept = dept;
				implicitHST += 1.0f;		
			} else if (grantDistribution.containsKey(dept)) {
				hsts.put(dept, hst * grantDistribution.get(dept));
				implicitHST -= grantDistribution.get(dept);
			} else {
				hsts.put(dept, 0.0f);			
			}			
		}

		if (implicitDept != null) {
			hsts.put(implicitDept, hst * implicitHST);
		}
	
		return hsts;
	
	}
	
	public Map<Department,Float> computeGrants() {		
		return computeGrantDist(computeCIGrant());
	}

	public Map<Department,Float> computeHST() {		
		return computeHSTDist(this.getCourse().getCredits()/60 * this.getModelStudentNumber());
	}

	public Map<Department,Float> computeAdjustedGrants() {
		return computeGrantDist(computeAdjustedCIGrant());
	}	
	public Map<Department,Float> computeGrantAdjustment() {
		return GrantMaps.diff(computeAdjustedGrants(),computeGrants());
	}
	 
 	public Float computeCIGrant() {
 		return fundingModel.computeFunding(getModelStudentNumber(),course.getCredits(),economyDoc.getBaseValue(),this.firstInstance);
 	}
 	public Float computeAdjustedCIGrant() {
 		return this.registeredStudents == null ? 0.0f : fundingModel.computeFunding(registeredStudents,course.getCredits(),economyDoc.getBaseValue(),this.firstInstance);
 	}
 	
/* 
	private Integer getRegStudentAcnt() {
		Integer students = 0;
		if (startRegStudents == null || startRegStudents == 0) {
			students = preceedingCI == null ?  0 : preceedingCI.getRegisteredStudents();
		} else {
			students = startRegStudents;
		}
		
		return students; 
 
   }
 */

	public Integer getModelStudentNumber() {
		log.debug(this.getDesignation()+", " + this.economyDoc.getYear() +": " + this.registeredStudents+", " +this.startRegStudents+", " +this.lRegStud+", " +this.uRegStud+", " +this.preceedingCI);
		Integer currentStudents = 1000;
		
		if (this.registeredStudents == null || this.registeredStudents == 0) {
			if (this.startRegStudents == null || this.startRegStudents == 0) {
				if (this.preceedingCI == null || this.preceedingCI == this) {
					currentStudents = DEFAULT_REG_STUDENT_NUMBER;
				} else {
					currentStudents = this.preceedingCI.getModelStudentNumber(); 
				}
			} else {
				currentStudents = this.startRegStudents; 
			}
		} else {
			currentStudents = this.registeredStudents;
		};
				
		if (economyDoc.isLocked()) {
			this.uRegStud = 0;
			if (this.lRegStud == null || this.lRegStud == 0) {
				this.lRegStud = currentStudents;
			}
	 		return lRegStud;
		} else {
			this.lRegStud = 0;
			this.uRegStud = currentStudents;
	 		return uRegStud;
		}

 		
   }
   
/* 
	public Integer getComputedStudents() {
		Integer studs = 0;
 		if (economyDoc.isLocked()) {
			if (this.lRegStud == null || this.lRegStud == 0) {
				studs = currentStudents;
			} else {
				studs =  lRegStud;
			}
		} else {
			studs = currentStudents;
		}
		return studs;  
	}
 */
	
	public Map<Department,Float> explicitGrantDist() { 
		Map<Department,Float> grants = new HashMap<Department,Float>();
		Float implicitGrant = 0.0f;
		Department implicitDept = null;
 
		for (Department dept : economyDoc.getAccountedDepts()) {
			if (dept.isImplicit()) {
				implicitDept = dept;
				implicitGrant += 1.0f;		
			} else if (grantDistribution.containsKey(dept)) {
				grants.put(dept, grantDistribution.get(dept));
				implicitGrant -= grantDistribution.get(dept);
			} else {
				grants.put(dept, 0.0f);			
			}			
		}

		if (implicitDept != null) {
			grants.put(implicitDept, implicitGrant);
		}
	
		return grants;
	
	}
	
	public Map<Department,Float> computeAccumulatedGrantAdjustment() {
		Map<Department,Float> adjustment = new HashMap<Department,Float>();
		log.debug("Adjustment for " + this.getDesignation() + ", " + this.economyDoc.getYear() + ", "+ this.computeGrantAdjustment());		
		try {		
			if(!(this.registeredStudents == null || this.registeredStudents == 0)) {
				if(this.preceedingCI == null || this.preceedingCI == this) {
					adjustment = this.computeGrantAdjustment();
				} else {
					adjustment = this.preceedingCI.isBalanceRequest() ? this.computeGrantAdjustment() : GrantMaps.sum(this.computeGrantAdjustment(), this.preceedingCI.computeAccumulatedGrantAdjustment());
			
				}
			}
		} catch (Exception e) {
			log.error("Caught a pesky exception " + e+ ", " +e.getCause());
		} finally {
			return adjustment;
		}

	}

    @Override
    public int compareTo(CourseInstance ci)
    {
        int cmp = 0;
        if (this.getDesignation() != null)
        {
            cmp = this.getDesignation().compareTo(ci.getDesignation());
        }
        return cmp;
    }
    
    
    
 
/* 
    public CourseInstance copyToNewEDoc(EconomyDocument ed) {
 
    	CourseInstance nci = new CourseInstance();
    	
		nci.setCourse( this.course );
		nci.setExtraDesignation( this.extraDesignation );
		nci.setFirstInstance( false );
		nci.setRegisteredStudents( null );
		nci.setStartRegStudents( this.registeredStudents);

		nci.setPreceedingCI( this.preceedingCI );
		nci.setEconomyDoc( ed );
		nci.setNote( this.note );
		nci.setBalancedEconomyDoc( this.balancedEconomyDoc);
		nci.setFundingModel( this.fundingModel );
   
   		return nci;
    	
    }
 */
 
}

