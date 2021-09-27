
package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import org.hibernate.annotations.GenericGenerator;

import se.uu.ebc.luntan.enums.Department;
import se.uu.ebc.luntan.enums.CourseGroup;
import se.uu.ebc.luntan.aux.GrantMaps;

import lombok.extern.slf4j.Slf4j;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Singular;

@Slf4j
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ECONOMYDOC", uniqueConstraints= @UniqueConstraint(columnNames={"YEAR"}))
public class EconomyDocument  extends Auditable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ID")
    private Long id;
    
    @OneToMany(mappedBy = "balancedEconomyDoc")
    private Set<CourseInstance> balancedCourseInstances = new HashSet<CourseInstance>();    
 
    @OneToMany(mappedBy = "economyDoc")
    private Set<CourseInstance> courseInstances = new HashSet<CourseInstance>();

    @OneToMany(mappedBy = "economyDoc")
    private Set<EconomyDocGrant> economyDocGrants = new HashSet<EconomyDocGrant>();
    
    @Column(name = "YEAR")
    @NotNull
    private Integer year;
    
    @Column(name = "BASE_VALUE", length = 255)
    @NotNull
    private Integer baseValue;
    
    @Column(name = "LOCKED")
    private boolean locked = false;

    @Column(name = "LOCK_DATE")
    private Date lockDate;

    @Column(name = "REGS_VALID")
    private boolean registrationsValid = false;
    
    @Column(name = "NOTE", length = 255)
    private String note;
  
    @Singular
    @ElementCollection(targetClass=Department.class)
	@Enumerated(EnumType.STRING)    
    private Set<Department> accountedDepts = new HashSet<Department>();
 	
     
	/* Constructors */
	

	public EconomyDocument(Integer year, Integer baseValue, boolean locked, String note, Set<Department> accounetedDepts) {
		this.year = year;
		this.baseValue = baseValue;
		this.note = note;
		this.locked = locked;
		this.accountedDepts = accountedDepts;	
	}


	/* Setters and getters */
	
	public void setLocked(boolean locked) {
		if (locked ^ this.locked) {
			if (locked) {
				this.lockDate = new Date();
			} else {
				this.lockDate = null;
			}
		} 
		this.locked = locked;	
	}

	/* Business methods */

	 public List<Department> getAccountedDeptsSorted()
	 {
		return asSortedList(this.accountedDepts);
	 }

	private static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
	  List<T> list = new ArrayList<T>(c);
	  java.util.Collections.sort(list);
	  return list;
	}
	
	public Integer getNumberOfCourseInstances() {
		return this.courseInstances == null ? 0 : this.courseInstances.size();
	}

	public Map<Department,Float> grandTotalSumSupplement() {
		return totalSumSupplement();
	}
	public Map<Department,Float> grandTotalSum() {
		Map<Department,Float> bigSum = totalSum();
		for (EconomyDocGrant edg : this.economyDocGrants) {
			bigSum = GrantMaps.sum(bigSum, edg.getDistributedGrant());
		}
		log.debug("grandTotalSum(): " + bigSum);
		bigSum = GrantMaps.sum(bigSum, totalAdjustmentSum());
		log.debug("grandTotalSum(), with adjustments: " + bigSum);
		return bigSum;
	}
	
	public 	Map<Department,Float> totalSumSupplement() {
		return	totalSum(true);
	}	
	public 	Map<Department,Float> totalSum() {
		return	totalSum(false);
	}	
	private	Map<Department,Float> totalSum(boolean supplementaryCIs) {
		Map<Department,Float> bigSum = new HashMap<Department,Float>();
		for (CourseInstance ci : courseInstances.stream().filter(ci -> ci.isSupplementary() == supplementaryCIs).collect(Collectors.toSet())) {
			bigSum = (GrantMaps.sum(bigSum, ci.computeGrants()) );
		}
		return bigSum;
	}

	public	Map<Department,Float> totalAdjustmentSum() {
		Map<Department,Float> bigSum = new HashMap<Department,Float>();
		for (CourseInstance ci : balancedCourseInstances) {
			bigSum = (GrantMaps.sum(bigSum, ci.computeAccumulatedGrantAdjustment()) );
		}
		return bigSum;
	}

	public  Map<CourseGroup,Map<Department,Float>> sumByCourseGroupSupplement() {
		return sumByCourseGroup (true);
	}	
	public  Map<CourseGroup,Map<Department,Float>> sumByCourseGroup() {
		return sumByCourseGroup (false);
	}	
	private Map<CourseGroup,Map<Department,Float>> sumByCourseGroup(boolean supplementaryCIs) {
		Map<CourseGroup,Map<Department,Float>> bigSum = new HashMap<CourseGroup,Map<Department,Float>>();
		for (CourseInstance ci : courseInstances.stream().filter(ci -> ci.isSupplementary() == supplementaryCIs).collect(Collectors.toSet())) {
			if (!bigSum.containsKey(ci.getCourse().getCourseGroup())) {
				bigSum.put(ci.getCourse().getCourseGroup(), new HashMap<Department,Float>());
			}
			bigSum.put(ci.getCourse().getCourseGroup(), GrantMaps.sum(bigSum.get(ci.getCourse().getCourseGroup()), ci.computeGrants()) );
		}
		
		return bigSum;
	}

	public  Map<CourseGroup,Map<Department,Float>> sumAdjustmentsByCourseGroupSupplement() {
		return sumAdjustmentsByCourseGroup(true);
	}
	public  Map<CourseGroup,Map<Department,Float>> sumAdjustmentsByCourseGroup() {
		return sumAdjustmentsByCourseGroup(false);
	}
	private Map<CourseGroup,Map<Department,Float>> sumAdjustmentsByCourseGroup(boolean supplementaryCIs) {
		Map<CourseGroup,Map<Department,Float>> bigSum = new HashMap<CourseGroup,Map<Department,Float>>();
		for (CourseInstance ci : courseInstances.stream().filter(ci -> ci.isSupplementary() == supplementaryCIs).collect(Collectors.toSet())) {
			if (!bigSum.containsKey(ci.getCourse().getCourseGroup())) {
				bigSum.put(ci.getCourse().getCourseGroup(), new HashMap<Department,Float>());
			}
			bigSum.put(ci.getCourse().getCourseGroup(), GrantMaps.sum(bigSum.get(ci.getCourse().getCourseGroup()), ci.computeGrantAdjustment()) );
		log.debug("Accumulated adjustment for " + ci.getDesignation() + ", " + ci.getEconomyDoc().getYear() + ", "+ ci.computeAccumulatedGrantAdjustment());		
		}
		
		return bigSum;
	}

	public  Map<CourseGroup,Map<Department,Float>> hstByCourseGroupSupplement() {
		return hstByCourseGroup(true);
	}
	public  Map<CourseGroup,Map<Department,Float>> hstByCourseGroup() {
		return hstByCourseGroup(false);
	}
	private Map<CourseGroup,Map<Department,Float>> hstByCourseGroup(boolean supplementaryCIs) {
		Map<CourseGroup,Map<Department,Float>> bigSum = new HashMap<CourseGroup,Map<Department,Float>>();
		for (CourseInstance ci : courseInstances.stream().filter(ci -> ci.isSupplementary() == supplementaryCIs).collect(Collectors.toSet())) {
			if (!bigSum.containsKey(ci.getCourse().getCourseGroup())) {
				bigSum.put(ci.getCourse().getCourseGroup(), new HashMap<Department,Float>());
			}
			bigSum.put(ci.getCourse().getCourseGroup(), GrantMaps.sum(bigSum.get(ci.getCourse().getCourseGroup()), ci.computeHST()) );
		}
		
		return bigSum;
	}

}
