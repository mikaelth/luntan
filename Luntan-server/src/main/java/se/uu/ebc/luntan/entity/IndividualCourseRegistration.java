package  se.uu.ebc.luntan.entity;


import java.util.Date;


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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import java.util.Set;
import java.util.List;

import java.util.HashSet;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;


import org.hibernate.annotations.GenericGenerator;

import lombok.extern.slf4j.Slf4j;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import se.uu.ebc.luntan.enums.*;
import se.uu.ebc.luntan.entity.IndividualCourseTeacher;

@Slf4j
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "INDIVIDUAL_COURSE_REGISTRATION")
public class IndividualCourseRegistration extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ID")
    private Long id;

	@Column(name = "STARTDATE")
	private Date startDate;

	@NotNull
	@Column(name = "STUDENT")
	private String studentName;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "COORDDEPT")
	private Department department;

	@NotNull
	@Column(name = "IBGREG")
	private boolean ibgReg;

	@Column(name = "DONE")
	private boolean studentDone;

	@Column(name = "COURSEVALUATION")
	private boolean courseEvalSetUp;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "COURSE_BAG_FK")
	private IndividualYearlyCourse courseBag;

    @ManyToOne
    @JoinColumn(name = "CREDIT_BASIS_FK")
	private IndividualCourseCreditBasis creditBasisRecord;

	@Column(name = "NOTE")
	private String note;

	@OneToMany(mappedBy = "assignment",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<IndividualCourseTeacher> teachers = new HashSet<IndividualCourseTeacher>();

	// Business methods


	public IndividualCourseTeacher getCoordinator() {
		IndividualCourseTeacher coordinator = teachers
			.stream()
  			.filter( c -> c.getTeacherType().equals (IndCourseTeacherKind.Coordinator) )
			.collect( Collectors.collectingAndThen( Collectors.toList(), list ->list.get(0) ));
		return coordinator;
	}

	public List<IndividualCourseTeacher> getSupervisors() {
		List<IndividualCourseTeacher> supervisors = teachers
			.stream()
  			.filter( c -> c.getTeacherType().equals (IndCourseTeacherKind.Supervisor) )
			.collect( Collectors.toList() );
		return supervisors;
	}

	public List<IndividualCourseTeacher> getReaders() {
		List<IndividualCourseTeacher> readers = teachers
			.stream()
  			.filter( c -> c.getTeacherType().equals (IndCourseTeacherKind.Reader) )
			.collect( Collectors.toList() );
		return readers;
	}

	public List<IndividualCourseTeacher> getSuperAndReader() {
		List<IndividualCourseTeacher> supervisors = teachers
			.stream()
  			.filter( c -> ((c.getTeacherType().equals (IndCourseTeacherKind.Supervisor)) || (c.getTeacherType().equals (IndCourseTeacherKind.Reader))) )
			.collect( Collectors.toList() );
		return supervisors;
	}

	public boolean isAssignedCreditRecord() {
		return creditBasisRecord == null ? false : true;
	}

	public Date getRegistrationDate() {
		return this.creationDate;
	}

	public Integer getStudents() {
		return 1;
	}

	public Float computeSupervisorsGrant() {
		return this.courseBag.computeSuperGrant(1);
//		return this.courseBag.computeSuperGrant(1)/(getSupervisors().isEmpty() ? 1 : getSupervisors().size());
	}

	public Float computeReadersGrant() {
//		return this.courseBag.computeReadGrant(1)/(getReaders().isEmpty() ? 1 : getReaders().size());

		return computeSupervisorsGrant() == 0.0f ? this.courseBag.computeReadGrant(1) : 0.0f;

//		return computeSupervisorsGrant() == 0.0f ? (this.courseBag.computeReadGrant(1)/( getReaders().isEmpty() ? 1 : lots) ) : 0.0f;
//		return computeSupervisorsGrant() == 0.0f ? (this.courseBag.computeReadGrant(1)/(getReaders().isEmpty() ? 1 : getReaders().size())) : 0.0f;
	}

	public Integer getReadersTeachFactors() {
		return this.getReaders()
			.stream()
			.mapToInt (x -> x.getTeachFactor())
			.sum();
	}

	public Integer getSupervisorsTeachFactors() {
		return this.getSupervisors()
			.stream()
			.mapToInt (x -> x.getTeachFactor())
			.sum();
	}
}
