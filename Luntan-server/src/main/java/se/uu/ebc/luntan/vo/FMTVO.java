package se.uu.ebc.luntan.vo;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import se.uu.ebc.luntan.entity.FundingModel;

import org.apache.log4j.Logger;

public class FMTVO {

    
    private static Logger logger = Logger.getLogger(FMTVO.class.getName());
	 
    private Long fmId;
    private Integer number;
    private Float value;
	
 	/* Setters and getters */
 	   


    public Long getFmId()
    {
    	return this.fmId;
    }

    public void setFmId(Long fmId)
    {
    	this.fmId = fmId;
    }


    public Integer getNumber()
    {
    	return this.number;
    }

    public void setNumber(Integer number)
    {
    	this.number = number;
    }


    public Float getValue()
    {
    	return this.value;
    }

    public void setValue(Float value)
    {
    	this.value = value;
    }



    
    /* Public methods */

  
 	/* Constructors */

	
	public FMTVO() {}

}
