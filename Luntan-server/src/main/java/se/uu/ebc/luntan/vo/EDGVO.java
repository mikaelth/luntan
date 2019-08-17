package se.uu.ebc.luntan.vo;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import se.uu.ebc.luntan.entity.EconomyDocGrant;
import se.uu.ebc.luntan.enums.EDGKind;
import se.uu.ebc.luntan.enums.Department;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.Value;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class EDGVO {

    
    private Long id;

	private Long economyDocId;
	
	private String itemDesignation;
    private EDGKind grantKind;
    private Float totalGrant;
    private Map<Department,Float> grantDistribution;
    private String note;
	

 	/* Setters and getters */

 	/* Constructors */

	public EDGVO (EconomyDocGrant xe) {

		this.id = xe.getId();
		this.economyDocId = xe.getEconomyDoc().getId();
		
		this.itemDesignation =  xe.getItemDesignation();
		this.grantKind =  xe.getGrantKind();		
		this.totalGrant = xe.getTotalGrant();
		this.grantDistribution = xe.getGrantDistribution();
		this.note = xe.getNote();	
		
	}
	

}
