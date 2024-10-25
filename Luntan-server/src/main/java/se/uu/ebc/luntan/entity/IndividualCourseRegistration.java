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

import org.hibernate.annotations.GenericGenerator;

import lombok.extern.slf4j.Slf4j;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import se.uu.ebc.luntan.enums.*;


@Slf4j
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class IndividualCourseRegistration extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "COURSE_BAG_FK")
	private IndividualYearlyCourse courseBag;


	@NotNull
	@Column(name = "STUDENTS")
	private Integer students=1;

	@NotNull
	@Column(name = "DATE")
	private Date date;

	@Column(name = "STARTDATE")
	private Date startDate;

	@NotNull
	@Column(name = "DEPARTMENT")
	private Department department;

	@Column(name = "NOTE")
	private String note;

	@Column(name = "STUDENT")
	private String studentName;

	@Column(name = "COORDINATOR")
	private String coordinator;

	@Column(name = "SUPERVISOR")
	private String supervisor;

	@Column(name = "SUBJECT_READER")
	private String subjectReader;

	// Business methods

}
