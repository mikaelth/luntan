package  se.uu.ebc.luntan.entity;


import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.persistence.DiscriminatorValue;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

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
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
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
		log.debug("computeCreditFunds() for student {}",this.assignment.getStudentName())	;
		log.debug("Teacher kind is {}",this.teacherType);
		if (this.teacherType.equals (IndCourseTeacherKind.Supervisor)) {
			if (this.notUU && (this.assignment.getCreationDate().after(NO_EXTERNAL_SUPERVISORS_PAID))) {
				log.debug("Teacher is supervisor and external {}",this,notUU);
				grant = 0.0f;
			} else {
				log.debug("Teacher is supervisor and internal {}",this,notUU);
				grant = this.assignment.computeSupervisorsGrant()*this.getTeachFactor()/this.assignment.getSupervisorsTeachFactors();
			}
		} else if (this.teacherType.equals(IndCourseTeacherKind.Reader) && this.assignment.isIbgReg()) {
			log.debug("Teaacher is reader and IBG-registration is {}, readers grant is {}", this.assignment.isIbgReg(), this.assignment.computeReadersGrant());
			grant = this.assignment.computeReadersGrant()*this.getTeachFactor()/this.assignment.getReadersTeachFactors();
		}

		log.debug("Credit grant is {} for {} and student {}", grant, this.teacherType, this.assignment.getStudentName());

		return grant;
	}

	public String getDeptAndName() {

		return this.department + this.name + this.fullDepartment;
//		return this.fullDepartment + this.name;
//		return this.department + this.name;

	}

	public Integer getTeachFactor() {
		Integer theFactor =  (tFactor == null || tFactor == 0) ? 1 : tFactor;
		log.debug("The tFactor is {} so the value used is {}", tFactor,theFactor);
		return theFactor;
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
