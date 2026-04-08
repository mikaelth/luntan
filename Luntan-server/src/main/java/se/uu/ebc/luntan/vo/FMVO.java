package se.uu.ebc.luntan.vo;

import java.util.Map;

import se.uu.ebc.luntan.entity.FundingModel;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class FMVO {



	private Long id;

    private String designation;
    private String expression;
    private String note;
	private Integer numCI;

    private Map<Integer,Float> valueTable;

 	/* Setters and getters */

    /* Public methods */


 	/* Constructors */


}
