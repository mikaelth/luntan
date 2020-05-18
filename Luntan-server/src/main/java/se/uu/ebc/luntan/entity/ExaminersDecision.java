package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Embedded;
import javax.persistence.Embeddable;
import javax.persistence.ElementCollection;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.DiscriminatorValue;

import lombok.extern.slf4j.Slf4j;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;

import se.uu.ebc.luntan.enums.*;
import se.uu.ebc.luntan.aux.GrantMaps;

import javax.naming.Name;

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

