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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Transient;
import jakarta.persistence.DiscriminatorValue;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;


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

import module java.naming;

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
		log.debug("computeSupervisorsGrant(), " + this.getDesignation());
 		return 0.0f;
 	}

	@Override
	public Float computeSuperGrant(Integer students) {
		log.debug("computeSuperGrant(), " + this.getDesignation());
 		return 0.0f;
 	}

	@Override
 	public Float computeReadersGrant() {
		log.debug("computeReadersGrant(), " + this.getDesignation());
 		return this.computeReadGrant(getModelStudentNumber());
 	}

	@Override
 	public Float computeReadGrant(Integer students) {
		log.debug("computeReadGrant(), number of students: " + students);
// 		return fundingModel.computeReaderFunding(students,course.getCredits(),economyDoc.getBaseValue(),this.firstInstance);
 		return students*this.economyDoc.getReaderBaseValue()*this.course.getCredits()/TEKNAT_READER_BASE_CREDITS;
 	}


}
