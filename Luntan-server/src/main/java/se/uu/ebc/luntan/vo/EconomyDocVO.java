package se.uu.ebc.luntan.vo;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;

import se.uu.ebc.luntan.entity.EconomyDocument;
import se.uu.ebc.luntan.enums.CIDesignation;
import se.uu.ebc.luntan.enums.Department;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class EconomyDocVO extends AuditableVO {

    private Long id;
    private Integer year;
    private Integer baseValue;
	private boolean locked = false;
    private String note;

	private Integer numberOfCIs;
	
	private Set<Department> accountedDepts = new HashSet<Department>();

	private boolean cloneCourses;
	
	/* Setters and getters */
/* 
	
    public Long getId()
    {
    	return this.id;
    }

    public void setId(Long id)
    {
    	this.id = id;
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


	public Integer getNumberOfCIs()
	{
		return this.numberOfCIs;
	}

	public void setNumberOfCIs(Integer numberOfCIs)
	{
		this.numberOfCIs = numberOfCIs;
	}


    public String getNote()
    {
    	return this.note;
    }

    public void setNote(String note)
    {
    	this.note = note;
    }

	 public Set<Department> getAccountedDepts()
	 {
		return this.accountedDepts;
	 }

	 public void setAccountedDepts(Set<Department> accountedDepts)
	 {
		this.accountedDepts = accountedDepts;
	 }
	 
	 public void setLocked(boolean locked) {
	 	this.locked = locked;
	 }

	public boolean isLocked () {
		return this.locked;
	}
	
	public boolean getLocked () {
		return this.locked;
	}

 */

	/* Constructors */
	
//	public EconomyDocVO() {}
	
	public EconomyDocVO(EconomyDocument xe) {
		super(xe);
		
		this.id = xe.getId();
		this.year = xe.getYear();
		this.baseValue = xe.getBaseValue();
		this.note = xe.getNote();
		this.accountedDepts = xe.getAccountedDepts();
		this.numberOfCIs = xe.getNumberOfCourseInstances();
		
		this.locked = xe.isLocked();
		this.cloneCourses = false;
	}
    
}
