package se.uu.ebc.luntan.service;


import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;
//import java.util.stream.Gatherers;
//import java.util.stream.Gatherer;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;

// import org.springframework.ldap.core.AttributesMapper;
// import org.springframework.ldap.core.LdapTemplate;
// import org.springframework.ldap.filter.AndFilter;
// import org.springframework.ldap.filter.EqualsFilter;
// import org.springframework.ldap.query.LdapQuery;
// import org.springframework.ldap.query.SearchScope;
// import org.springframework.ldap.support.LdapUtils;
// import org.springframework.ldap.support.LdapNameBuilder;
// import static org.springframework.ldap.query.LdapQueryBuilder.query;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.extern.slf4j.Slf4j;

import se.uu.ebc.luntan.repo.CourseRepo;
import se.uu.ebc.luntan.repo.ExaminerRepo;
import se.uu.ebc.luntan.repo.ExaminersDecisionRepo;
import se.uu.ebc.luntan.repo.ExaminersListRepo;
import se.uu.ebc.luntan.repo.ExaminersWorkingListRepo;
import se.uu.ebc.luntan.service.StaffService;

import se.uu.ebc.luntan.vo.BPPersonVO;
import se.uu.ebc.luntan.vo.ExListVO;
import se.uu.ebc.luntan.entity.Examiner;
import se.uu.ebc.luntan.entity.ExaminersDecision;
import se.uu.ebc.luntan.entity.ExaminersList;
import se.uu.ebc.luntan.entity.ExaminersWorkingList;
import se.uu.ebc.luntan.enums.EduBoard;


@Service
@Slf4j
public class BPService {

    @Autowired
	CourseRepo courseRepo;

	@Autowired
	StaffService staffService;
	
    @Autowired
	ExaminersListRepo exlRepo;

 
 
 	/* Examiners */


	public List<BPPersonVO> examinersOnLatestDecisionList() {
		
		ExaminersList exl = exlRepo.findLatestDecision();
		
/* 
		List<ExaminerVO> exvoList = exl.getExaminers().stream()
			.map( e -> new ExaminerVO(e))
			.collect(Collectors.toList());
 */

		List<BPPersonVO> exvoList = exl.getExaminers().stream()
			.map( e -> BPPersonVO.builder()
				.lastModifiedBy(e.getLastModifiedBy())
				.lastModifiedDate(e.getLastModifiedDate())
				.courseCode(e.getCourse().getCode())
//				.firstName(staffService.findbyEmployeeNumber(e.getExaminer()).getGivenName())
//				.lastName(staffService.findbyEmployeeNumber(e.getExaminer()).getFamilyName())
				.ldapEntry(e.getExaminer())
//				.userName(staffService.findbyEmployeeNumber(e.getExaminer()).getUsername())
				.build()
			)
			.collect(Collectors.toList());


		log.debug("examinersOnLatestDecisionList, theList "+ReflectionToStringBuilder.toString(exl, ToStringStyle.MULTI_LINE_STYLE));
		
		return exvoList;
	}

 
}