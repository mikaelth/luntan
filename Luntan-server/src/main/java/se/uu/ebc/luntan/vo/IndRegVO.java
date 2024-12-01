package se.uu.ebc.luntan.vo;


import se.uu.ebc.luntan.entity.IndividualCourseRegistration;
import se.uu.ebc.luntan.enums.Department;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class IndRegVO {

	private Long id;

	private boolean courseEvalSetUp;
	private boolean studentDone;

	private Date startDate;
	private Date registrationDate;

	private String note;
	private String studentName;
	private boolean ibgReg;

 	private Long creditBasisRecId;
	private Long courseInstanceId;
	private Long economyDocId;

	private Department department;
//	private Long coordinatorId;

    /* Public methods */


 	/* Constructors */

	public IndRegVO (IndividualCourseRegistration xe) {

		log.debug("In ExaminerVO constructor, " + xe);


		this.id = xe.getId();

		this.courseEvalSetUp = xe.isCourseEvalSetUp();
		this.studentDone = xe.isStudentDone();
		this.startDate = xe.getStartDate();
		this.registrationDate = xe.getRegistrationDate();
		this.note = xe.getNote();
		this.studentName = xe.getStudentName();
		this.department = xe.getDepartment();
		this.ibgReg = xe.isIbgReg();

		this.creditBasisRecId = xe.getCreditBasisRecord() == null ? null : xe.getCreditBasisRecord().getId();
//		this.coordinatorId = xe.getCoordinator() == null ? null : xe.getCoordinator().getId();;
		this.courseInstanceId = xe.getCourseBag()== null ? null : xe.getCourseBag().getId(); ;
		this.economyDocId = xe.getCourseBag()== null ? null : xe.getCourseBag().getEconomyDoc().getId(); ;


	}


}
