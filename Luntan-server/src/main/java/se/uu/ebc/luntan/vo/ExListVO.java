package se.uu.ebc.luntan.vo;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;

import se.uu.ebc.luntan.entity.Course;
import se.uu.ebc.luntan.entity.ExaminersList;
import se.uu.ebc.luntan.entity.ExaminersDecision;
import se.uu.ebc.luntan.enums.EduBoard;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class ExListVO extends AuditableVO{

    private Long id;
    private EduBoard board;
    private boolean decided;
    private Date decisionDate;
    private String note;
	private List<String> defaultExaminers = new ArrayList<String>();
    
    /* Public methods */

	
 	/* Constructors */

	public ExListVO (ExaminersList xe) {

		super(xe);

		log.debug("In ExListVO constructor, " + xe);

		this.id = xe.getId();
		this.note = xe.getNote();
		this.decided = xe.decided();
		
		if (xe instanceof ExaminersDecision){
			this.board = ((ExaminersDecision)xe).getBoard();
			this.decisionDate = ((ExaminersDecision)xe).getDecisionDate();
			this.defaultExaminers = ((ExaminersDecision)xe).getDefaultExaminers();}
		}
	

}
