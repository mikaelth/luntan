package se.uu.ebc.luntan.entity;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Column;


import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.extern.slf4j.Slf4j;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Slf4j
@Getter
public abstract class Auditable {

    @CreatedBy
    @Column(name = "created_by")
    protected String createdBy;

    @CreatedDate
 //   @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    protected LocalDateTime creationDate;

    @LastModifiedBy
    @Column(name = "modified_by")
    protected String lastModifiedBy;

    @LastModifiedDate
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modification_date")
    protected LocalDateTime lastModifiedDate;


	/* Setters and getters */

/* 
    public String getLastModifiedBy()
    {
    	return this.lastModifiedBy;
    }
 */

/* 
    public void setLastModifiedBy(String lastModifiedBy)
    {
    	this.lastModifiedBy = lastModifiedBy;
    }
 */

/* 
    public Date getLastModifiedDate()
    {
    	return this.lastModifiedDate;
    }

    public Date getCreationDate()
    {
    	return this.creationDate;
    }

 */
	
}
