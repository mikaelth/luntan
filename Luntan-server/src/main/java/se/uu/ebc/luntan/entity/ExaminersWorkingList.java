package  se.uu.ebc.luntan.entity;


import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import se.uu.ebc.luntan.enums.*;


@Slf4j
@Data
@Entity
@DiscriminatorValue("1") 
public class ExaminersWorkingList extends ExaminersList {

 
 	// Business methods
 	@Override
 	public boolean decided() {
 		return false;
 	}
}

