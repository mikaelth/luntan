package se.uu.ebc.luntan.vo;

import java.util.Set;
import java.util.HashSet;

import se.uu.ebc.luntan.entity.EconomyDocument;
import se.uu.ebc.luntan.enums.Department;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.NoArgsConstructor;

@Slf4j
@Data
@NoArgsConstructor
public class EconomyDocVO extends AuditableVO {

    private Long id;
    private Integer year;
    private Integer baseValue;
	private boolean locked = false;
	private boolean registrationsValid;
    private String note;

	private Integer numberOfCIs;
	
	private Set<Department> accountedDepts = new HashSet<Department>();

	private boolean cloneCourses;
	
	/* Setters and getters */

	/* Constructors */
	
	
	public EconomyDocVO(EconomyDocument xe) {
		super(xe);
		
		this.id = xe.getId();
		this.year = xe.getYear();
		this.baseValue = xe.getBaseValue();
		this.note = xe.getNote();
		this.accountedDepts = xe.getAccountedDepts();
		this.numberOfCIs = xe.getNumberOfCourseInstances();
		
		this.locked = xe.isLocked();
		this.registrationsValid = xe.isRegistrationsValid();
		this.cloneCourses = false;
	}
    
}
