package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import org.hibernate.annotations.GenericGenerator;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import se.uu.ebc.luntan.enums.*;


@Slf4j
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="LIST_STATUS", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("0") 
@Table(name = "EXAMINERS_LIST")
public abstract class ExaminersList extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ID")
    protected Long id;
    
/* 
	@Column(name = "DECISION_DATE")
	private Date decisionDate;
	
    @Column(name = "BOARD")
	@NotNull
	@Enumerated(EnumType.STRING)    
    private EduBoard board;
    

	@ElementCollection
	private List<String> defaultExaminers = new ArrayList<String>();
 */

    @Column(name = "NOTE")
    private String note;

    @OneToMany(mappedBy = "examinerList", cascade = CascadeType.ALL)
	protected Set<Examiner> examiners = new HashSet<Examiner>();

	@Column(name="LIST_STATUS", insertable = false, updatable = false)
	protected int listStatus;
 
 	// Business methods
 	public abstract boolean decided();
}

