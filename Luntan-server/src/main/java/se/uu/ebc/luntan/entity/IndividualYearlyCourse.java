package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.AbstractMap;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.lang.Math;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.ElementCollection;
import javax.persistence.Transient;
import javax.persistence.DiscriminatorValue;
import javax.validation.constraints.NotNull;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import org.hibernate.annotations.GenericGenerator;

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
//@Builder(toBuilder = true)
@NoArgsConstructor
//@AllArgsConstructor
@DiscriminatorValue("2")
@Entity
public class IndividualYearlyCourse  extends CourseInstance {


	@ElementCollection
	@MapKeyEnumerated(EnumType.STRING)
//    private Map<Department,Integer> predictedStudentDistribution = new HashMap<Department,Integer>();
    private Map<Department,Integer> predictedStudentDistribution;

	@ElementCollection
	@MapKeyEnumerated(EnumType.STRING)
//    private Map<Department,Integer> lockedStudentDistribution = new HashMap<Department,Integer>();
    private Map<Department,Integer> lockedStudentDistribution;

	@ElementCollection
	@MapKeyEnumerated(EnumType.STRING)
    private Map<Department,Integer> registeredStudentDistribution;

    @OneToMany(mappedBy = "courseBag")
	private List<IndividualCourseRegistration> registrationEvents;
	
	/* Business methods */


	private Map<Department,Float> computeGrantDist(Map<Department,Integer> deptDistribution, Float grantPerStudent, Integer students) {
		Map<Department,Float> grants = new HashMap<Department,Float>();
		Float implicitGrant = 0.0f;
		Department implicitDept = null;

		log.debug("Grant for " +getDesignation() +", " + students +" students and " + grantPerStudent + " per student");

		for (Department dept : economyDoc.getAccountedDepts()) {
			if (dept.isImplicit()) {
				implicitDept = dept;
				implicitGrant += students;
			} else if (deptDistribution.containsKey(dept)) {
				grants.put(dept, grantPerStudent * deptDistribution.get(dept));
				implicitGrant -= deptDistribution.get(dept);
			} else {
				grants.put(dept, 0.0f);
			}
		}

		if (implicitDept != null) {
			grants.put(implicitDept, grantPerStudent * implicitGrant);
		}

		return grants;
	}
	private Map<Department,Float> computeHSTDist(Float hst) {
		Map<Department,Float> hsts = new HashMap<Department,Float>();
		Float implicitHST = 0.0f;
		Department implicitDept = null;
		Integer totalStudents = predictedStudentDistribution.values().stream().reduce(0, Integer::sum);

		log.debug("HST for " +getDesignation() +", "+ hst);

		for (Department dept : economyDoc.getAccountedDepts()) {
			if (dept.isImplicit()) {
				implicitDept = dept;
				implicitHST += 1.0f;
			} else if (predictedStudentDistribution.containsKey(dept)) {
				hsts.put(dept, hst * predictedStudentDistribution.get(dept)/totalStudents);
				implicitHST -= predictedStudentDistribution.get(dept)/totalStudents;
			} else {
				hsts.put(dept, 0.0f);
			}
		}

		if (implicitDept != null) {
			hsts.put(implicitDept, hst * implicitHST);
		}

		return hsts;

	}

	private Integer registeredDistStudents() {
		log.debug("registeredDistStudents()");
		return registeredStudentDistribution == null ? 
			registrationEvents == null ? 0 : registrationEvents.stream().mapToInt(IndividualCourseRegistration::getStudents).sum() :
			registeredStudentDistribution.values().stream().reduce(0, Integer::sum);
	}
	private Integer predictedDistStudents() {
		return predictedStudentDistribution.values().stream().reduce(0, Integer::sum);
	}

	private IndividualYearlyCourse preceedingIYC() {
		return (IndividualYearlyCourse) this.preceedingCI;
	}
	
	private Map<Department, Integer> currentStudentsDist() {
//		log.debug("currentStudents(): " +this.getDesignation()+", " + this.economyDoc.getYear() +": " + this.registeredDistStudents()+", " +this.predictedDistStudents()+", " +this.preceedingCI);
		log.debug("currentStudents(): " +this.getDesignation()+", " + this.economyDoc.getYear());
		Map<Department, Integer> currentStudents = new HashMap<Department, Integer>();
try {

		if (registeredDistStudents() == 0) {
			if (this.predictedStudentDistribution == null || this.predictedDistStudents() == 0) {
				if (this.preceedingIYC()== null || this.preceedingIYC()== this) {
					this.modelCase=StudentModelNumberCase.DEFAULT;
					currentStudents = new HashMap<Department, Integer>();
				} else {
					if (this.preceedingIYC().preceedingIYC() == null || this.preceedingIYC().preceedingIYC() == this.preceedingIYC() || this.preceedingIYC().preceedingIYC().regStudDist().size() == 0) {
							if(this.preceedingIYC().regStudDist().size() == 0) {
								this.modelCase=StudentModelNumberCase.PREVIOUSMODEL;
								currentStudents = this.preceedingIYC().predStudDist();
							} else {
								this.modelCase=StudentModelNumberCase.PREVIOUSREG;
								currentStudents = this.preceedingIYC().regStudDist();
							}
					} else {
							this.modelCase=StudentModelNumberCase.REG2YEARS;
							currentStudents = this.preceedingIYC().preceedingIYC().regStudDist();
					}
				}
			} else {
				this.modelCase=StudentModelNumberCase.EXPLICTISTART;
				currentStudents = this.predStudDist();
			}
		} else {
			this.modelCase=StudentModelNumberCase.REGISTERED;
			currentStudents = this.regStudDist();
		};
	} catch (Exception e) {
			log.error("Caught a pesky exception " + e+ ", " +e.getCause());
	
	} finally {

		log.debug(this.modelCase.toString());
		return currentStudents;
	}
	}

	public Map<Department,Integer> getModelStudentNumberDist() {
	log.debug("getModelStudentNumber(): " + this.getDesignation()+", " + this.economyDoc.getYear() +": " + this.registeredStudents+", "  +this.preceedingCI);
	if (economyDoc.isLocked()) {
		if (!this.lockedStudentNumberUpdated) {
			this.lockedStudentDistribution = new HashMap<Department,Integer>(currentStudentsDist()); //  Trying to fix an error
			this.lockedStudentNumberUpdated = true;
		} else {
			this.modelCase=StudentModelNumberCase.LOCKED;
		}
		return lockedStudentDistribution;
	} else {
		log.debug("getModelStudentNumberDist(), pre: " + this.getDesignation()+", " + this.economyDoc.getYear() +": " + this.lockedStudentNumberUpdated);
		this.lockedStudentNumberUpdated = false;
		log.debug("getModelStudentNumberDist(), post: " + this.getDesignation()+", " + this.economyDoc.getYear() +": " + this.lockedStudentNumberUpdated);
		return currentStudentsDist();
	}
   }


	public Map<Department, Integer>	regStudDist() {
		return registeredStudentDistribution == null ? 
			registeredStudentDistribution : 
			studentRegCountDist();
	}
	public Map<Department, Integer>	predStudDist() {
		return this.predictedStudentDistribution;
	}
	
	public boolean isRegCongruent() {
		return registeredDistStudents().equals(this.registeredStudents);
	}
	
	public Map<Department, Integer> studentRegCountDist() {
		Map<Department, Integer> summary = new HashMap<Department, Integer>();
		if (this.registrationEvents.size() > 0) {
			for (IndividualCourseRegistration regEvent : this.registrationEvents) {
				if (summary.containsKey(regEvent.getDepartment())) {
					summary.put(regEvent.getDepartment(), summary.get( regEvent.getDepartment() ) + regEvent.getStudents() );
				} else {
					summary.put(regEvent.getDepartment(),regEvent.getStudents());
				}			
			}
		}
		return summary;
	}
	
	
	@Override
	public Map<Department,Float> computeGrants() {
		log.debug("computeGrants()");
		return computeGrantDist( this.getModelStudentNumberDist(), fundingModel.computeFunding(1,course.getCredits(),economyDoc.getBaseValue(),this.firstInstance,false), getModelStudentNumber() );
	}

 	/**
	 * <p>Calculates the grants based on actual registrations for the course instance
	 * </p>
	 * @return a map with amounts by department
	 */
	@Override
	public Map<Department,Float> computeAdjustedGrants() {
		log.debug("computeAdjustedGrants()");
		return (this.registeredStudentDistribution != null) ? 
			computeGrantDist( this.registeredStudentDistribution, fundingModel.computeFunding(1,course.getCredits(),economyDoc.getBaseValue(),this.firstInstance,false), registeredStudents ) :
			computeGrants();
	}

	@Override
 	public Float computeCIGrant() {
		log.debug("computeCIGrant()");
 		return fundingModel.computeFunding(getModelStudentNumber(),course.getCredits(),economyDoc.getBaseValue(),this.firstInstance,false);
 	}

	@Override
 	public Float computeSupervisorsGrant() {
		log.debug("computeSupervisorsGrant()");
 		return fundingModel.computeFunding(getModelStudentNumber(),course.getCredits(),economyDoc.getBaseValue(),this.firstInstance,true);
 	}

	@Override
	public boolean needBalancedDocument () {
		return ( this.balanceRequest && this.balancedEconomyDoc == null && this.registeredStudents != null && this.registrationValid && this.registeredStudentDistribution != null);
	}

	@Override
	public Integer getModelStudentNumber() {
		return getModelStudentNumberDist().values().stream().reduce(0, Integer::sum);
	}
	

}
