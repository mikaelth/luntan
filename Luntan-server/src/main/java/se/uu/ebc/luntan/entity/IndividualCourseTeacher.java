package  se.uu.ebc.luntan.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import lombok.extern.slf4j.Slf4j;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import se.uu.ebc.luntan.enums.*;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;


@Slf4j
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "INDIVIDUAL_COURSE_TEACHER")
public class IndividualCourseTeacher extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "INDIVIDUAL_COURSE_REGISTRATION_FK")
	private IndividualCourseRegistration assignment;

	@NotNull
	@Column(name = "LDAP_ENTRY")
	private String ldapEntry;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "DEPARTMENT")
	private Department department;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "TEACHER_TYPE")
	private IndCourseTeacherKind teacherType;

	@Column(name="NOT_UU")
	private boolean notUU = false;

	@Column(name="EXTERNAL")
	private boolean external = false;

	@Column(name="TFACTOR")
	private Integer tFactor;

	@Column(name="NAME")
	private String name;

	@Column(name="FULL_DEPARTMENT")
	private String fullDepartment;

	@Column(name="PHONE")
	private String phone;

	@Column(name="EMAIL")
	private String email;


	@Column(name = "NOTE")
	private String note;

	// Business methods

	private static final Date NO_EXTERNAL_SUPERVISORS_PAID = new GregorianCalendar(2025, Calendar.MARCH, 31).getTime();

	public Float computeCreditFunds() {
		Float grant = 0.0f;
		log.debug("computeCreditFunds()")	;
		if (this.teacherType.equals (IndCourseTeacherKind.Supervisor)) {
			if (this.notUU && (this.assignment.getCreationDate().after(NO_EXTERNAL_SUPERVISORS_PAID))) {
				grant = 0.0f;
			} else {
				grant = this.assignment.computeSupervisorsGrant()*this.getTeachFactor()/this.assignment.getSupervisorsTeachFactors();
			}
		} else if (this.teacherType.equals(IndCourseTeacherKind.Reader) && this.assignment.isIbgReg()) {
			grant = this.assignment.computeReadersGrant()*this.getTeachFactor()/this.assignment.getReadersTeachFactors();
		}

		return grant;
	}

	public String getDeptAndName() {

		return this.department + this.name + this.fullDepartment;
//		return this.fullDepartment + this.name;
//		return this.department + this.name;

	}

	public Integer getTeachFactor() {
		return (tFactor == null || tFactor == 0) ? 1 : tFactor;
	}

	public Float getTeachFactorFraction() {
		Float teachFraction = 1.0f;

		if (this.teacherType.equals (IndCourseTeacherKind.Supervisor)) {
			teachFraction = ((float)this.getTeachFactor())/this.assignment.getSupervisorsTeachFactors();
		} else if (this.teacherType.equals (IndCourseTeacherKind.Reader)) {
			teachFraction = ((float)this.getTeachFactor())/this.assignment.getReadersTeachFactors();
		}

log.debug("Factor " + teachFraction + ", teach " + getTeachFactor() +", " + teacherType.toString());

		return teachFraction;

	}
}
