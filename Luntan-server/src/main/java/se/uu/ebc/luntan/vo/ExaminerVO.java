package se.uu.ebc.luntan.vo;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import se.uu.ebc.luntan.entity.Course;
import se.uu.ebc.luntan.entity.Examiner;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class ExaminerVO {

    private Long id;
    private Long decisionId;
    private Long courseId;
    private String ldapEntry;
    private Integer rank;
    private String note;
    private boolean decided;
	
    
    /* Public methods */

	
 	/* Constructors */

	public ExaminerVO (Examiner xe) {

		log.debug("In ExaminerVO constructor, " + xe);
		
		this.id = xe.getId();
		
		this.decisionId = xe.getExaminerList().getId();
		this.courseId = xe.getCourse().getId();
		this.ldapEntry = xe.getExaminer();
		this.rank = xe.getRank();
		this.note = xe.getNote();
		this.decided = xe.decided();
	}
	

}
