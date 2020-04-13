package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

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
import javax.persistence.Transient;
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
import lombok.AccessLevel;

import se.uu.ebc.luntan.enums.*;
import se.uu.ebc.luntan.aux.GrantMaps;

import javax.naming.Name;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    
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

    @Column(name = "LCKUPDATED")
    @Setter(AccessLevel.NONE)
    private boolean lockedStudentNumberUpdated = false;

/* 
    @Column(name = "UNLOCKEDSTUDENTNUMBER")
	private Integer uRegStud;
 */

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

/* 
	@ElementCollection
//	@MapKeyEnumerated(EnumType.STRING)    
//    private Set<Name> examiners = new HashSet<Name>();;
//	  private Map<String,Integer> examiners = new LinkedHashMap<String, Integer>();
//    private List<String> examiners = new ArrayList<String>();
    private List<Examiner> examiners = new ArrayList<Examiner>();
//    private Set<String> examiners = new HashSet<String>();
 */


	@Transient
	private StudentModelNumberCase modelCase = StudentModelNumberCase.DEFAULT;
	
	
	/* Setters and getters */
 

	/* Constructors */
	
	
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
		log.debug("computeGrants()");
		return computeGrantDist(computeCIGrant());
	}

	public Map<Department,Float> computeHST() {		
		return computeHSTDist(this.getCourse().getCredits()/60 * this.getModelStudentNumber());
	}

	/**
	 * <p>Calculates the grants based on actual registrations for the course instance
	 * </p>
	 * @return a map with amounts by department
	 * @since 1.0
	 */
	public Map<Department,Float> computeAdjustedGrants() {
		log.debug("computeAdjustedGrants()");
		return computeGrantDist(computeAdjustedCIGrant());
	}	

	/**
	 * <p>Calculates the difference between modeled (budget) grants 
	 * and grants based on actual registrations for the course instance
	 * </p>
	 * @return a map with amounts by department
	 * @since 1.0
	 */
	public Map<Department,Float> computeGrantAdjustment() {
		return GrantMaps.diff(computeAdjustedGrants(),computeGrants());
	}
	 
 	public Float computeCIGrant() {
 		return fundingModel.computeFunding(getModelStudentNumber(),course.getCredits(),economyDoc.getBaseValue(),this.firstInstance);
 	}
 	public Float computeAdjustedCIGrant() {
// 		return this.registeredStudents == null ? 0.0f : fundingModel.computeFunding(registeredStudents,course.getCredits(),economyDoc.getBaseValue(),this.firstInstance);
		return this.registeredStudents == null ? computeCIGrant() : fundingModel.computeFunding(registeredStudents,course.getCredits(),economyDoc.getBaseValue(),this.firstInstance);
 	}
 	
	
	
	private Integer currentStudents() {
		log.debug("currentStudents(): " +this.getDesignation()+", " + this.economyDoc.getYear() +": " + this.registeredStudents+", " +this.startRegStudents+", " +this.lRegStud+", " +this.preceedingCI);
		Integer currentStudents = 1000;
		
		if (this.registeredStudents == null || this.registeredStudents == 0) {
			if (this.startRegStudents == null || this.startRegStudents == 0) {
				if (this.preceedingCI == null || this.preceedingCI == this) {
					this.modelCase=StudentModelNumberCase.DEFAULT;
					currentStudents = DEFAULT_REG_STUDENT_NUMBER;
				} else {
					if (this.preceedingCI.getPreceedingCI() == null || this.preceedingCI.getPreceedingCI() == this.preceedingCI || this.preceedingCI.getPreceedingCI().getRegisteredStudents() == null) {
							if(this.preceedingCI.getRegisteredStudents() == null || this.preceedingCI.getRegisteredStudents() == 0) {
								this.modelCase=StudentModelNumberCase.PREVIOUSMODEL;
								currentStudents = this.preceedingCI.getModelStudentNumber();
							} else {
								this.modelCase=StudentModelNumberCase.PREVIOUSREG;
								currentStudents = this.preceedingCI.getRegisteredStudents();							
							}
					} else {
							this.modelCase=StudentModelNumberCase.REG2YEARS;
							currentStudents = this.preceedingCI.getPreceedingCI().getRegisteredStudents();
					}
				}
			} else {
				this.modelCase=StudentModelNumberCase.EXPLICTISTART;
				currentStudents = this.startRegStudents; 
			}
		} else {
			this.modelCase=StudentModelNumberCase.REGISTERED;
			currentStudents = this.registeredStudents;
		};

		log.debug(this.modelCase.toString());		
		return currentStudents;
	}
				
	public Integer getModelStudentNumber() {
		log.debug("getModelStudentNumber(): " + this.getDesignation()+", " + this.economyDoc.getYear() +": " + this.registeredStudents+", " +this.startRegStudents+", " +this.lRegStud+", " +", " +this.preceedingCI);
		if (economyDoc.isLocked()) {
			if (!this.lockedStudentNumberUpdated) {
				this.lRegStud = currentStudents();
				this.lockedStudentNumberUpdated = true;
			} else {
				this.modelCase=StudentModelNumberCase.LOCKED;
			}
	 		return lRegStud;
		} else {
			this.lockedStudentNumberUpdated = false;
	 		return currentStudents();
		} 		
   }
   
   
	public void updateLock() {
		log.debug("updateLock(): " + this.getDesignation()+", " + this.economyDoc.getYear() +": " + this.registeredStudents+", " +this.startRegStudents+", " +this.lRegStud+", " +", " +this.preceedingCI);
		getModelStudentNumber();
	}
	
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
	
	/**
	 * <p>Calculates the accumulated adjustment (computeGrantAdjustment()) of grants, 
	 * back to the latest instance which has been adjusted
	 * </p>
	 * @return a map with amounts by department
	 * @since 1.0
	 */
	public Map<Department,Float> computeAccumulatedGrantAdjustment() {
		Map<Department,Float> adjustment = new HashMap<Department,Float>();
		try {		
//			if(!(this.registeredStudents == null || this.registeredStudents == 0)) {
			if(this.preceedingCI == null || this.preceedingCI == this) {
				adjustment = this.computeGrantAdjustment();
			} else {
				adjustment = this.preceedingCI.isBalanceRequest() ? this.computeGrantAdjustment() : GrantMaps.sum(this.computeGrantAdjustment(), this.preceedingCI.computeAccumulatedGrantAdjustment());			
			}
//			}
		} catch (Exception e) {
			log.error("Caught a pesky exception " + e+ ", " +e.getCause());
		} finally {
			log.debug("Accumulated adjustment for " + this.getDesignation() + ", " + this.economyDoc.getYear() + ", "+ adjustment);		
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
    
    
    
 
 
}

