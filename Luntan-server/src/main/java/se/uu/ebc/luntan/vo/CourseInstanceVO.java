package se.uu.ebc.luntan.vo;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import se.uu.ebc.luntan.entity.CourseInstance;
import se.uu.ebc.luntan.entity.FundingModel;
import se.uu.ebc.luntan.enums.CIDesignation;
import se.uu.ebc.luntan.enums.Department;

import org.apache.log4j.Logger;

public class CourseInstanceVO {

    
    private static Logger logger = Logger.getLogger(CourseInstanceVO.class.getName());
	 
    private Long id;

	private boolean locked; 
	
	private Long courseId;
	private String courseGroup;
	private String courseDesignation;
	
	private Long preceedingCIId;
	private Long economyDocId;
	private Long balancedEconomyDocId;
	private Long fundingModelId;

	private CIDesignation extraDesignation;

	private Integer registeredStudents;
	private Integer startRegStudents;
	private Integer modelStudentNumber;
    private String note;
    private boolean balanceRequest = false;
	private boolean firstInstance;

    private Map<Department,Float> grantDistribution;
	
 	/* Setters and getters */
 	   

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}



	public Long getCourseId()
	{
		return this.courseId;
	}

	public void setCourseId(Long courseId)
	{
		this.courseId = courseId;
	}


	public Long getPreceedingCIId()
	{
		return this.preceedingCIId;
	}

	public void setPreceedingCIId(Long preceedingCIId)
	{
		this.preceedingCIId = preceedingCIId;
	}


	public Long getEconomyDocId()
	{
		return this.economyDocId;
	}

	public void setEconomyDocId(Long economyDocId)
	{
		this.economyDocId = economyDocId;
	}


	public Long getBalancedEconomyDocId()
	{
		return this.balancedEconomyDocId;
	}

	public void setBalancedEconomyDocId(Long balancedEconomyDocId)
	{
		this.balancedEconomyDocId = balancedEconomyDocId;
	}


	public Long getFundingModelId()
	{
		return this.fundingModelId;
	}

	public void setFundingModelId(Long fundingModelId)
	{
		this.fundingModelId = fundingModelId;
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


	public Integer getModelStudentNumber()
	{
		return this.modelStudentNumber;
	}

	public void setModelStudentNumber(Integer modelStudentNumber)
	{
		this.modelStudentNumber = modelStudentNumber;
	}


    public String getNote()
    {
    	return this.note;
    }

    public void setNote(String note)
    {
    	this.note = note;
    }


    public boolean getBalanceRequest()
    {
    	return this.balanceRequest;
    }

    public boolean isBalanceRequest()
    {
    	return this.balanceRequest;
    }
    
    public void setBalanceRequest(boolean balanceRequest)
    {
    	this.balanceRequest = balanceRequest;
    }


	public Map<Department,Float> getGrantDistribution() {
		return this.grantDistribution;
	}

	public void setGrantDistribution(Map<Department,Float> grantDistribution) {
		this.grantDistribution = grantDistribution;
	}
    

	public boolean isLocked()
	{
		return this.locked;
	}

	public void setLocked(boolean locked)
	{
		this.locked = locked;
	}


	public String getCourseGroup()
	{
		return this.courseGroup;
	}

	public void setCourseGroup(String courseGroup)
	{
		this.courseGroup = courseGroup;
	}


	public String getCourseDesignation()
	{
		return this.courseDesignation;
	}

	public void setCourseDesignation(String courseDesignation)
	{
		this.courseDesignation = courseDesignation;
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

		this.id = xe.getId();
		
		this.courseId = xe.getCourse().getId();
		this.courseGroup =  xe.getCourse().getCourseGroup().toString();
		this.courseDesignation =  xe.getCourse().getDesignation();
		
		this.preceedingCIId = xe.getPreceedingCI() == null ? null : xe.getPreceedingCI().getId();
		this.economyDocId = xe.getEconomyDoc().getId();
		this.locked = xe.getEconomyDoc().isLocked();
		
		this.balancedEconomyDocId = xe.getBalancedEconomyDoc() == null ? null : xe.getBalancedEconomyDoc().getId();
		this.fundingModelId = xe.getFundingModel().getId();
		
		this.extraDesignation = xe.getExtraDesignation();
		this.registeredStudents = xe.getRegisteredStudents();
		this.startRegStudents = xe.getStartRegStudents();

		this.balanceRequest = xe.isBalanceRequest();
		this.firstInstance = xe.getFirstInstance();		

		this.grantDistribution = xe.getGrantDistribution();
		this.modelStudentNumber = xe.getModelStudentNumber();
		
		this.note = xe.getNote();	

	}
	
	public CourseInstanceVO() {}

}
