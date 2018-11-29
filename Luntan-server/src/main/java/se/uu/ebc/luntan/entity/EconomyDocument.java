
package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

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

import se.uu.ebc.luntan.enums.Department;
import se.uu.ebc.luntan.enums.CourseGroup;
import se.uu.ebc.luntan.aux.GrantMaps;

import org.apache.log4j.Logger;

@Entity
@Table(name = "ECONOMYDOC", uniqueConstraints= @UniqueConstraint(columnNames={"YEAR"}))
public class EconomyDocument  extends Auditable {

    private static Logger log = Logger.getLogger(EconomyDocument.class.getName());

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

    
 
    @OneToMany(mappedBy = "economyDoc")
    private Set<CourseInstance> courseInstances;

    
    @Column(name = "YEAR")
    @NotNull
    private Integer year;
    
    @Column(name = "BASE_VALUE", length = 255)
    @NotNull
    private Integer baseValue;
    
    @Column(name = "LOCKED", length = 255)
    private boolean locked = false;
    
    @Column(name = "NOTE", length = 255)
    private String note;
  
    @ElementCollection(targetClass=Department.class)
	@Enumerated(EnumType.STRING)    
    private Set<Department> accountedDepts = new HashSet<Department>();
 	
     
	/* Constructors */
	
	public EconomyDocument() {}

	public EconomyDocument(Integer year, Integer baseValue, boolean locked, String note, Set<Department> accounetedDepts) {
		this.year = year;
		this.baseValue = baseValue;
		this.note = note;
		this.locked = locked;
		this.accountedDepts = accountedDepts;	
	}


	/* Setters and getters */

	 public Set<Department> getAccountedDepts()
	 {
		return this.accountedDepts;
	 }

	 public void setAccountedDepts(Set<Department> accountedDepts)
	 {
		this.accountedDepts = accountedDepts;
	 }
 
    public Integer getYear()
    {
    	return this.year;
    }

    public void setYear(Integer year)
    {
    	this.year = year;
    }

    
    public Integer getBaseValue()
    {
    	return this.baseValue;
    }

    public void setBaseValue(Integer baseValue)
    {
    	this.baseValue = baseValue;
    }

    
    public boolean isLocked() {
    	return this.locked;
    }
    public void setLocked(boolean locked) {
    	this.locked=locked;
    }
    
    public String getNote()
    {
    	return this.note;
    }

    public void setNote(String note)
    {
    	this.note = note;
    }


    public Set<CourseInstance> getCourseInstances()
    {
    	return this.courseInstances;
    }

    public void setCourseInstances(Set<CourseInstance> courseInstances)
    {
    	this.courseInstances = courseInstances;
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
	
	public  Map<CourseGroup,Map<Department,Float>> sumByCourseGroup() {
		Map<CourseGroup,Map<Department,Float>> bigSum = new HashMap<CourseGroup,Map<Department,Float>>();
		for (CourseInstance ci : courseInstances) {
			if (!bigSum.containsKey(ci.getCourse().getCourseGroup())) {
				bigSum.put(ci.getCourse().getCourseGroup(), new HashMap<Department,Float>());
			}
			bigSum.put(ci.getCourse().getCourseGroup(), GrantMaps.sum(bigSum.get(ci.getCourse().getCourseGroup()), ci.computeGrants()) );
		}
		
		return bigSum;
	}
	public  Map<CourseGroup,Map<Department,Float>> sumAdjustmentsByCourseGroup() {
		Map<CourseGroup,Map<Department,Float>> bigSum = new HashMap<CourseGroup,Map<Department,Float>>();
		for (CourseInstance ci : courseInstances) {
			if (!bigSum.containsKey(ci.getCourse().getCourseGroup())) {
				bigSum.put(ci.getCourse().getCourseGroup(), new HashMap<Department,Float>());
			}
			bigSum.put(ci.getCourse().getCourseGroup(), GrantMaps.sum(bigSum.get(ci.getCourse().getCourseGroup()), ci.computeGrantAdjustment()) );
		log.debug("Accumulated adjustment for " + ci.getDesignation() + ", " + ci.getEconomyDoc().getYear() + ", "+ ci.computeAccumulatedGrantAdjustment());		
		}
		
		return bigSum;
	}

	public  Map<CourseGroup,Map<Department,Float>> hstByCourseGroup() {
		Map<CourseGroup,Map<Department,Float>> bigSum = new HashMap<CourseGroup,Map<Department,Float>>();
		for (CourseInstance ci : courseInstances) {
			if (!bigSum.containsKey(ci.getCourse().getCourseGroup())) {
				bigSum.put(ci.getCourse().getCourseGroup(), new HashMap<Department,Float>());
			}
			bigSum.put(ci.getCourse().getCourseGroup(), GrantMaps.sum(bigSum.get(ci.getCourse().getCourseGroup()), ci.computeHST()) );
		}
		
		return bigSum;
	}

}
