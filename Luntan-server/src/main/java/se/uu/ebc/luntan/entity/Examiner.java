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
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
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