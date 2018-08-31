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


@Entity
@Table(name = "COURSE")
public class Course  extends Auditable {

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
    
    @Column(name = "CODE", length = 255)
    @NotNull
    private String code;
    
    @Column(name = "SE_NAME", length = 255)
    @NotNull
    private String seName;
    
    @Column(name = "COURSE_GROUP", length = 255)
    private String courseGroup;
    
    @Column(name = "NOTE", length = 255)
    private String note;
    
    @Column(name = "CREDITS", precision = 12)
    @NotNull
    private Float credits;
    
 
 
 	/* Constructors */
 	
 	public Course() {}
 	
 	public Course (String code, String seName, Float credits, String note) {
 		this.code = code;
 		this.seName = seName;
 		this.credits = credits;
 		this.note = note;
 	}
     


	/* Setters and getters */
 
/* 
    public Set<CourseInstance> getCourseInstances() {
        return courseInstances;
    }
    
    public void setCourseInstances(Set<CourseInstance> courseInstances) {
        this.courseInstances = courseInstances;
    }
 */
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getSeName() {
        return seName;
    }
    
    public void setSeName(String seName) {
        this.seName = seName;
    }
    
    
    public String getCourseGroup() {
        return courseGroup;
    }
    
    public void setCourseGroup(String courseGroup) {
        this.courseGroup = courseGroup;
    }
    
    
	   
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public Float getCredits() {
        return credits;
    }
    
    public void setCredits(Float credits) {
        this.credits = credits;
    }
    
}
