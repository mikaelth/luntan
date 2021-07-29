package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.DiscriminatorValue;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import se.uu.ebc.luntan.enums.*;


@Slf4j
@Data
@DiscriminatorValue("2")
@Entity
public class ExaminersDecision extends ExaminersList {

/* 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
 */
    
	@Column(name = "DECISION_DATE")
	private Date decisionDate;
	
    @Column(name = "BOARD")
	@NotNull
	@Enumerated(EnumType.STRING)    
    private EduBoard board;
    
	@ElementCollection
	private List<String> defaultExaminers = new ArrayList<String>();

/* 
    @OneToMany(mappedBy = "examinerDecision")
	private Set<Examiner> examiners = new HashSet<Examiner>();
 */


 	// Business methods
 	@Override
 	public boolean decided() {
 		return true;
 	}
 
}

