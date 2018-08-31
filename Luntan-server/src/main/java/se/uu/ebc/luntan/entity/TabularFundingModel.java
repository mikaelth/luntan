package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ElementCollection;


@Entity
@Table(name = "TABULAR_MODEL")
public  class TabularFundingModel  extends FundingModel {

    
/* 
    @OneToMany(mappedBy = "course")
    private Set<CourseInstance> courseInstances;
 */
    
    @Column(name = "DESIGNATION", length = 255)
    private String designation;

    @ElementCollection
    private Map<Integer,Float> valueTable;
    
 
 
     


	/* Setters and getters */
 
    public String getDesignation()
    {
    	return this.designation;
    }

    public void setDesignation(String designation)
    {
    	this.designation = designation;
    }

 
 	/* Business methods */
	@Override
	public Float computeFunding(Integer registerdStudents, Float ects, Integer baseLevel) {
		return 0.0f;
	}   
}
