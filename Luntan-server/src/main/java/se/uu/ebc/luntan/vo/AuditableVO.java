package se.uu.ebc.luntan.vo;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;

import se.uu.ebc.luntan.entity.Auditable;

import org.apache.log4j.Logger;

public class AuditableVO {

//     private String createdBy;
//     private Date creationDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;



	/* Setters and getters */
	

/* 
    public String getCreatedBy()
    {
    	return this.createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
    	this.createdBy = createdBy;
    }


    public Date getCreationDate()
    {
    	return this.creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
    	this.creationDate = creationDate;
    }

 */

    public String getLastModifiedBy()
    {
    	return this.lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy)
    {
    	this.lastModifiedBy = lastModifiedBy;
    }


    public Date getLastModifiedDate()
    {
    	return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate)
    {
    	this.lastModifiedDate = lastModifiedDate;
    }



	/* Constructors */
	
	public AuditableVO() {}
	
	public AuditableVO(Auditable xe) {

// 		this.createdBy = xe.getCreatedBy();
// 		this.creationDate = xe.getCreationDate();
		this.lastModifiedBy = xe.getLastModifiedBy();
		this.lastModifiedDate = xe.getLastModifiedDate();
	}
    
}
