package se.uu.ebc.luntan.vo;


import se.uu.ebc.luntan.entity.Course;
import se.uu.ebc.luntan.enums.CourseGroup;
import se.uu.ebc.luntan.enums.EduBoard;
import se.uu.ebc.luntan.enums.Department;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class CourseVO {

	 
    private Long id;

    private String code;
    private String seName;
    private CourseGroup courseGroup;
    private String note;
    private Float credits;
	private Department examinerDepartment;
	private EduBoard board;
    private boolean inactive;
		
 	/* Setters and getters */
 	   
    
    /* Public methods */

	
 	/* Constructors */

	public CourseVO (Course xe) {

		log.debug("In CourseVO constructor, " + xe);
		
		this.id = xe.getId();
		
		this.code = xe.getCode();
		this.seName = xe.getSeName();
		this.courseGroup = xe.getCourseGroup();
		this.examinerDepartment = xe.getExaminerDepartment();
		this.board = xe.getBoard();
		this.note = xe.getNote();
		this.credits = xe.getCredits();
		this.inactive = xe.isInactive();
	}
	

}
