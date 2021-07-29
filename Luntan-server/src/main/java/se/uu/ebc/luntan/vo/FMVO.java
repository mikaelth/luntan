package se.uu.ebc.luntan.vo;

import java.util.Map;

import se.uu.ebc.luntan.entity.FundingModel;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class FMVO {

    
//    private static Logger logger = Logger.getLogger(FMVO.class.getName());
	 
	private Long id;
	   
    private String designation;
    private String expression;
    private String note;
	private Integer numCI;
	
    private Map<Integer,Float> valueTable;

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


    public String getDesignation()
    {
    	return this.designation;
    }

    public void setDesignation(String designation)
    {
    	this.designation = designation;
    }


    public String getExpression()
    {
    	return this.expression;
    }

    public void setExpression(String expression)
    {
    	this.expression = expression;
    }


    public String getNote()
    {
    	return this.note;
    }

    public void setNote(String note)
    {
    	this.note = note;
    }

 
	public Integer getNumCI()
	{
		return this.numCI;
	}

	public void setNumCI(Integer numCI)
	{
		this.numCI = numCI;
	}

 	public void setValueTable(Map<Integer,Float> valueTable) {
 		this.valueTable = valueTable;
 	}
 	public Map<Integer,Float> getValueTable() {
 		return this.valueTable;
 	}

 */
    
    /* Public methods */

  
 	/* Constructors */

	public FMVO (FundingModel xe) {
		this.id = xe.getId();

		this.designation = xe.getDesignation();
		this.expression = xe.getExpression();
		this.note = xe.getNote();

		this.numCI = xe.getNumCourseInstances();	

		this.valueTable = xe.getValueTable();
	}
	

}
