package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;


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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

