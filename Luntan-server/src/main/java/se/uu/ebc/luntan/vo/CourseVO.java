package se.uu.ebc.luntan.vo;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import se.uu.ebc.luntan.entity.Course;
import se.uu.ebc.luntan.entity.FundingModel;
import se.uu.ebc.luntan.enums.CIDesignation;
import se.uu.ebc.luntan.enums.CourseGroup;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class CourseVO {

    
//    private static Logger logger = Logger.getLogger(CourseVO.class.getName());
	 
    private Long id;

    private String code;
    private String seName;
    private CourseGroup courseGroup;
    private String note;
    private Float credits;
	
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




    public String getCode()
    {
    	return this.code;
    }

    public void setCode(String code)
    {
    	this.code = code;
    }


    public String getSeName()
    {
    	return this.seName;
    }

    public void setSeName(String seName)
    {
    	this.seName = seName;
    }


    public CourseGroup getCourseGroup()
    {
    	return this.courseGroup;
    }

    public void setCourseGroup(CourseGroup courseGroup)
    {
    	this.courseGroup = courseGroup;
    }


    public String getNote()
    {
    	return this.note;
    }

    public void setNote(String note)
    {
    	this.note = note;
    }


    public Float getCredits()
    {
    	return this.credits;
    }

    public void setCredits(Float credits)
    {
    	this.credits = credits;
    }

 */

    
    /* Public methods */

	
 	/* Constructors */

	public CourseVO (Course xe) {

		log.debug("In CourseVO constructor, " + xe);
		
		this.id = xe.getId();
		
		this.code = xe.getCode();
		this.seName = xe.getSeName();
		this.courseGroup = xe.getCourseGroup();
		this.note = xe.getNote();
		this.credits = xe.getCredits();
	}
	
//	public CourseVO() {}

}
