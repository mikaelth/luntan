package  se.uu.ebc.luntan.entity;

import java.util.Set;
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


@Entity
@Table(name = "FUNDING_MODEL")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class FundingModel  extends Auditable  implements FundingModelInterface {

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
    
    @Column(name = "DESIGNATION", length = 255)
    private String designation;

    
    
 
 
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
	
	public abstract Float computeFunding(Integer registerdStudents, Float ects, Integer baseLevel);    
}
