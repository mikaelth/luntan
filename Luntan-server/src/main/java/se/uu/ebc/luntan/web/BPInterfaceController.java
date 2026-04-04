package se.uu.ebc.luntan.web;
 
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

import java.lang.Number;
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.List;
import java.util.Map;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import se.uu.ebc.ldap.Staff;
import se.uu.ebc.luntan.entity.Course;
import se.uu.ebc.luntan.entity.CourseInstance;
import se.uu.ebc.luntan.entity.EconomyDocument;
import se.uu.ebc.luntan.entity.Examiner;
import se.uu.ebc.luntan.entity.ExaminersDecision;
import se.uu.ebc.luntan.entity.ExaminersList;
import se.uu.ebc.luntan.entity.FundingModel;
import se.uu.ebc.luntan.entity.Programme;
import se.uu.ebc.luntan.enums.*;
import se.uu.ebc.luntan.repo.CourseInstanceRepo;
import se.uu.ebc.luntan.repo.CourseRepo;
import se.uu.ebc.luntan.repo.EconomyDocumentRepo;
import se.uu.ebc.luntan.repo.ExaminerRepo;
import se.uu.ebc.luntan.repo.ExaminersDecisionRepo;
import se.uu.ebc.luntan.repo.ExaminersListRepo;
import se.uu.ebc.luntan.repo.FundingModelRepo;
import se.uu.ebc.luntan.repo.ProgrammeRepo;
import se.uu.ebc.luntan.service.BPService;
import se.uu.ebc.luntan.service.EconomyDocumentService;
import se.uu.ebc.luntan.service.FundingModelService;
import se.uu.ebc.luntan.service.StaffService;
import se.uu.ebc.luntan.vo.BPPersonVO;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value = "/bemanning")
@CrossOrigin(origins = "http://localhost:1841", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class BPInterfaceController {

	@Autowired
	EconomyDocumentRepo emRepo;

	@Autowired
	CourseInstanceRepo ciRepo;

 	@Autowired
	BPService bpService;


	/* Access for Bemanningsplaneraren to get relevant data */


	@GetMapping(value="bemanning/cgd")
	@ResponseBody
	public ResponseEntity<String> getCIGrantData(@RequestParam(value = "year", required = true) Integer year) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
 			EconomyDocument ed = emRepo.findByYear(year);
 			return new ResponseEntity<String>(new JSONSerializer()
 				.prettyPrint(true)
 				.exclude("*.class")
 				.rootName("courseinstances")
 				.transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate")
 				.serialize(ciRepo.findByEconomyDoc( ed ).stream()
 					.collect(Collectors
 						.toMap(CourseInstance::getShortDesignation, CourseInstance::computeRoundedGrants
 				)))
 			, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

	@RequestMapping(value="examiners", method = RequestMethod.GET)
//	@RequestMapping(value="examiners/{coursecode}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getExminers() {
//	public ResponseEntity<String> getCourseExminer(@PathVariable("coursecode") String courseCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
			log.debug("getExminers");

 			List<BPPersonVO> exVO = bpService.examinersOnLatestDecisionList();
 			return new ResponseEntity<String>(new JSONSerializer()
 				.prettyPrint(true)
 				.exclude("*.class")
 				.rootName("examiners")
 				.transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate")
 				.serialize(exVO)
 			, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

} 
