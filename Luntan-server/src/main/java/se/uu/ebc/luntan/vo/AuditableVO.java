package se.uu.ebc.luntan.vo;

import java.time.LocalDateTime;

import se.uu.ebc.luntan.entity.Auditable;


public class AuditableVO {

//     private String createdBy;
//     private Date creationDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;



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


    public LocalDateTime getLastModifiedDate()
    {
    	return this.lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate)
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
