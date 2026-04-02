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
@Table(name = "EXAMINER", uniqueConstraints=@UniqueConstraint(columnNames={"COURSE_FK","LIST_FK","LDAP_ENTRY", "RANK" }))
public class Examiner extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "LIST_FK")
	private ExaminersList examinerList;
	
    @ManyToOne
    @NotNull
    @JoinColumn(name = "COURSE_FK")
	private Course course;
	
	@NotNull
	@Column(name = "LDAP_ENTRY")
	private String examiner;

/* 
	@Column(name = "NAME")
	private String examiner;
 */

	@NotNull
	@Column(name = "RANK")
	private Integer rank;

	@Column(name = "NOTE")
	private String note;

	// Business methods
	
	public boolean decided() {
		return examinerList == null ? false : examinerList.decided();
	}
}