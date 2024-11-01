package se.uu.ebc.luntan.vo;

import se.uu.ebc.luntan.enums.IndCourseTeacherKind;
import se.uu.ebc.luntan.enums.Department;

import se.uu.ebc.luntan.entity.IndividualCourseTeacher;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class IndCourseTeacherVO {

    private Long id;
	private Long assignmentId;
	private String ldapEntry;
	private Department department;
	private IndCourseTeacherKind teacherType;

	private boolean external = false;

	private String name;
	private String fullDepartment;
	private String phone;
	private String email;

	private String note;


    /* Public methods */


 	/* Constructors */

	public IndCourseTeacherVO (IndividualCourseTeacher xe) {

		log.debug("In IndCourseTeacherVO constructor, " + xe);


		this.id = xe.getId();

		this.ldapEntry = xe.getLdapEntry();
		this.department = xe.getDepartment();
		this.teacherType = xe.getTeacherType();

		this.external = xe.isExternal();

		this.name = xe.getName();
		this.fullDepartment = xe.getFullDepartment();
		this.phone = xe.getPhone();
		this.email = xe.getEmail();

		this.note = xe.getNote();

		this.assignmentId = xe.getAssignment().getId();


	}


}
