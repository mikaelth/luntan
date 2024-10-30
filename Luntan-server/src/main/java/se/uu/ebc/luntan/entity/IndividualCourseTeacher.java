package  se.uu.ebc.luntan.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.hibernate.annotations.GenericGenerator;
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

	@Column(name = "NOTE")
	private String note;

	// Business methods
	
}