package se.uu.ebc.luntan.vo;


import se.uu.ebc.luntan.entity.IndividualCourseCreditBasis;
import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class IndCCBasisVO {

	private Long id;

    private LocalDateTime sent;
  	private LocalDateTime createdDate;
	private Integer NumberOfRegs;
    private String note;
   
    /* Public methods */

	
 	/* Constructors */

	public IndCCBasisVO (IndividualCourseCreditBasis xe) {

		log.debug("In IndCCBasisVO constructor, " + xe);
	

		this.id = xe.getId();
		
		this.NumberOfRegs = xe.getRegistrations().size();
		this.sent = xe.getSent();
		this.createdDate = xe.getCreationDate();
		
		this.note = xe.getNote();

	}
	

}
