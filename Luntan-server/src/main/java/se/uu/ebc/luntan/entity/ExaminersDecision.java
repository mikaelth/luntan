package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.DiscriminatorValue;

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
	private LocalDateTime decisionDate;
	
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

