package se.uu.ebc.luntan.vo;


import se.uu.ebc.luntan.entity.Programme;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class ProgrammeVO {


    private Long id;

    private String code;
    private String seName;
    private String direction;
    private String selmaPath;
    private String note;
    private boolean inactive;
    private String programDirector;
	private String linkId;

 	/* Setters and getters */


    /* Public methods */


 	/* Constructors */

	public ProgrammeVO (Programme xe) {

		log.debug("In ProgrammeVO constructor, " + xe);

		this.id = xe.getId();

		this.code = xe.getCode();
		this.seName = xe.getSeName();
		this.direction = xe.getDirection();
		this.programDirector = xe.getProgramDirector();
		this.note = xe.getNote();
		this.inactive = xe.isInactive();
		this.selmaPath = xe.getSELMAPath();
		this.linkId = xe.getLinkId();
	}

//	public CourseVO() {}

}
