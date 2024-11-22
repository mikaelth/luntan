package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.AbstractMap;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.lang.Math;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.ElementCollection;
import javax.persistence.Transient;
import javax.persistence.DiscriminatorValue;
import javax.validation.constraints.NotNull;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import org.hibernate.annotations.GenericGenerator;

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
//@Data
@Getter
@Setter
//@Builder(toBuilder = true)
@NoArgsConstructor
//@AllArgsConstructor
@DiscriminatorValue("3")
@Entity
public class IndividualYearlyTUNDegreeCourse extends IndividualYearlyCourse {

	private static final Float TEKNAT_READER_BASE_CREDITS = 60.0f;

	
	/* Business methods */
	
	@Override
 	public Float computeSupervisorsGrant() {
		log.debug("computeSupervisorsGrant()");
 		return 0.0f;
 	}

	public Float computeSuperGrant(Integer students) {
		log.debug("computeSupervisorsGrant()");
 		return 0.0f;
 	}


	@Override
 	public Float computeReadersGrant() {
		log.debug("computeReadersGrant()");
 		return computeReadGrant(getModelStudentNumber());
 	}

 	public Float computeReadGrant(Integer students) {
		log.debug("computeReadersGrant(), number of studennts: " + students);
// 		return fundingModel.computeReaderFunding(students,course.getCredits(),economyDoc.getBaseValue(),this.firstInstance);
 		return students*this.economyDoc.getReaderBaseValue()*this.course.getCredits()/TEKNAT_READER_BASE_CREDITS;
 	}


}
