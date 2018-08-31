package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.HashSet;

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

import se.uu.ebc.luntan.enums.Department;

@Entity
@Table(name = "ECONOMYDOC", uniqueConstraints= @UniqueConstraint(columnNames={"YEAR"}))
public class EconomyDocument  extends Auditable {

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

    
/* 
    @OneToMany(mappedBy = "course")
    private Set<CourseInstance> courseInstances;
 */
    
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
  
    @ElementCollection
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

}
