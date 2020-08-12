package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import org.hibernate.annotations.GenericGenerator;

import se.uu.ebc.luntan.enums.CourseGroup;
import se.uu.ebc.luntan.enums.EduBoard;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COURSE")
public class Course  extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ID")
    private Long id;
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

        
    @Column(name = "CODE", length = 255)
    @NotNull
    private String code;
    
    @Column(name = "SE_NAME", length = 255)
    @NotNull
    private String seName;
    
    @Column(name = "COURSE_GROUP", length = 255)
	@Enumerated(EnumType.STRING)    
    private CourseGroup courseGroup;
    
    @Column(name = "NOTE", length = 255)
    private String note;
    
    @Column(name = "CREDITS", precision = 12)
    @NotNull
    private Float credits;

    @Column(name = "BOARD", length = 255)
	@Enumerated(EnumType.STRING)    
    private EduBoard board;
    
/* 
    @OneToMany(mappedBy = "course")
    @OrderBy("rank ASC")
    private List<Examiner> examiners = new ArrayList<Examiner>();

    @OneToMany(mappedBy = "course")
    private Set<CourseInstance> courseInstances = new HashSet<CourseInstance>();
 
*/ 

 	/* Constructors */
 	
/* 
 	public Course() {}
 	
 	public Course (String code, String seName, Float credits, String note) {
 		this.code = code;
 		this.seName = seName;
 		this.credits = credits;
 		this.note = note;
 	}
     
 */
 	public Course (String code, String seName, Float credits, String note, EduBoard board) {
 		this.code = code;
 		this.seName = seName;
 		this.credits = credits;
 		this.note = note;
 		this.board = board;
 	}


	/* Setters and getters */
 
/* 
    public Set<CourseInstance> getCourseInstances() {
        return courseInstances;
    }
    
    public void setCourseInstances(Set<CourseInstance> courseInstances) {
        this.courseInstances = courseInstances;
    }
    
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
    
    
    public CourseGroup getCourseGroup() {
        return courseGroup;
    }
    
    public void setCourseGroup(CourseGroup courseGroup) {
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
 */
 
    
    /* Business methods */
    
    public String getDesignation() {
    	return this.code + " " + this.seName;
    }
    
    public void copyProps (Course c) {
    	this.code = c.getCode();
		this.seName = c.getSeName();
		this.courseGroup = c.getCourseGroup();
		this.note = c.getNote();
		this.credits = c.getCredits();
		this.board = c.getBoard();    
    }
}
