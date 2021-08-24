package se.uu.ebc.luntan.web;


//import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.access.annotation.Secured;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
//import se.uu.ebc.bemanning.util.DateNullTransformer;

import java.math.BigInteger;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;
import java.security.Principal;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import org.apache.log4j.Logger;

import se.uu.ebc.luntan.repo.EconomyDocumentRepo;
import se.uu.ebc.luntan.repo.FundingModelRepo;
import se.uu.ebc.luntan.repo.CourseRepo;
import se.uu.ebc.luntan.repo.ExaminersListRepo;
import se.uu.ebc.luntan.repo.ExaminerRepo;

import se.uu.ebc.luntan.service.EconomyDocumentService;
import se.uu.ebc.luntan.service.FundingModelService;
import se.uu.ebc.luntan.service.StaffService;
import se.uu.ebc.luntan.service.ExaminersService;

import se.uu.ebc.luntan.entity.CourseInstance;
import se.uu.ebc.luntan.entity.EconomyDocument ;
import se.uu.ebc.luntan.entity.FundingModel ;
import se.uu.ebc.luntan.entity.Course ;
import se.uu.ebc.luntan.entity.ExaminersList ;
import se.uu.ebc.luntan.entity.ExaminersDecision ;
import se.uu.ebc.luntan.entity.Examiner ;
import se.uu.ebc.ldap.Staff ;

import se.uu.ebc.luntan.enums.CourseGroup;
import se.uu.ebc.luntan.enums.Department;
import se.uu.ebc.luntan.enums.EduBoard;
import se.uu.ebc.luntan.enums.DiffKind;

import se.uu.ebc.luntan.vo.EconomyDocVO;
import se.uu.ebc.luntan.vo.EDGVO;

import se.uu.ebc.luntan.util.DateNullTransformer;

import se.uu.ebc.luntan.web.view.EconomyDocExcel;
import se.uu.ebc.luntan.web.view.ExaminersExcel;

@Slf4j
@Controller
@RequestMapping(value = "/")
@CrossOrigin(origins = "http://localhost:1841", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class EconomyDocController {

//    private Logger log = Logger.getLogger(EconomyDocController.class.getName());

	@Autowired
	EconomyDocumentRepo emRepo;

	@Autowired
	FundingModelRepo fmRepo;

	@Autowired
	FundingModelService fmService;

	@Autowired
	EconomyDocumentService edService;


 	@Autowired
	StaffService staffService;

 	@Autowired
	CourseRepo courseRepo;

 	@Autowired
	ExaminersListRepo elRepo;

 	@Autowired
	ExaminerRepo exRepo;

 	@Autowired
	ExaminersService exService;


	/* EconomyDocumentGrants */

	@RequestMapping(value="rest/edocgrants", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> allEDGrants() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("edocgrants").deepSerialize(edService.getAllEDGrants()), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

	@Secured({("ROLE_DEPARTMENTHEAD"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="rest/edocgrants/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateEDGrant(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			EDGVO edVO = new JSONDeserializer<EDGVO>().use(null, EDGVO.class).use(Date.class, new DateNullTransformer("yyyy-MM-dd") ).deserialize(json);
			log.debug("updateEDGrant, edVO "+ReflectionToStringBuilder.toString(edVO, ToStringStyle.MULTI_LINE_STYLE));
			edVO.setId(id);
			edVO = edService.saveEDGrant(edVO);

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("edocgrants").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(edVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@Secured({("ROLE_DEPARTMENTHEAD"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="rest/edocgrants", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createEDGrant(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			EDGVO edVO = new JSONDeserializer<EDGVO>().use(null, EDGVO.class).use(Date.class, new DateTransformer("yyyy-MM-dd") ).deserialize(json);
			edVO = edService.saveEDGrant(edVO);



            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+edVO.getId().toString()).build().toUriString());

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("edocgrants").transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate").deepSerialize(edVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@Secured({("ROLE_DEPARTMENTHEAD"),("ROLE_SUBJECTCOORDINATOR")})
	@RequestMapping(value = "rest/edocgrants/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteEDGrant(@PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			edService.deleteEDGrant(id);
            return new ResponseEntity<String>("{success: true, id : " +id.toString() + "}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	/* Funding models */

    @RequestMapping(value="rest/fundingmodels", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> allFMs() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
// 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("fundingmodels").transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate").deepSerialize(fmService.getAllFMs()), headers, HttpStatus.OK);
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("fundingmodels").transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate").deepSerialize(fmRepo.findAll()), headers, HttpStatus.OK);
// 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("fundingmodels").deepSerialize(fmRepo.findAll()), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

	@Secured({("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="rest/fundingmodels/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFM(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			FundingModel fm = new JSONDeserializer<FundingModel>().use(null, FundingModel.class).use(Date.class, new DateTransformer("yyyy-MM-dd") ).deserialize(json);
			fm = fmRepo.save(fm);

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("fundingmodels").transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate").deepSerialize(fm);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="rest/fundingmodels", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFM(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			FundingModel fm = new JSONDeserializer<FundingModel>().use(null, FundingModel.class).use(Date.class, new DateTransformer("yyyy-MM-dd") ).deserialize(json);
			fm = fmRepo.save(fm);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+fm.getId().toString()).build().toUriString());

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("fundingmodels").transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate").deepSerialize(fm);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_SUBJECTCOORDINATOR")})
	@RequestMapping(value = "rest/fundingmodels/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFM(@PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
//			fmRepo.delete(fmRepo.findById(id));
			fmRepo.delete(fmRepo.findById(id).get());
            return new ResponseEntity<String>("{success: true, id : " +id.toString() + "}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



	/* EconomyDocument */

	@RequestMapping(value="rest/edocs", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> allEDs() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("edocs").transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate").deepSerialize(edService.getAllEDs()), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

	@Secured({("ROLE_DEPARTMENTHEAD"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="rest/edocs/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateED(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			EconomyDocVO edVO = new JSONDeserializer<EconomyDocVO>().use(null, EconomyDocVO.class).use(Date.class, new DateNullTransformer("yyyy-MM-dd") ).deserialize(json);
			log.debug("updateED, edVO "+ReflectionToStringBuilder.toString(edVO, ToStringStyle.MULTI_LINE_STYLE));
			edVO.setId(id);
			edVO = edService.saveEDoc(edVO);

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("edocs").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(edVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@Secured({("ROLE_DEPARTMENTHEAD"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="rest/edocs", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createED(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			EconomyDocVO edVO = new JSONDeserializer<EconomyDocVO>().use(null, EconomyDocVO.class).use(Date.class, new DateTransformer("yyyy-MM-dd") ).deserialize(json);
			edVO = edService.saveEDoc(edVO);



            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+edVO.getId().toString()).build().toUriString());

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("edocs").transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate").deepSerialize(edVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@Secured({("ROLE_DEPARTMENTHEAD"),("ROLE_SUBJECTCOORDINATOR")})
	@RequestMapping(value = "rest/edocs/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteED(@PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			edService.deleteEDoc(id);
            return new ResponseEntity<String>("{success: true, id : " +id.toString() + "}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	/* Examiners */

    @RequestMapping(value = "/view/examiners", method = RequestMethod.GET)
    public String viewExaminers(@RequestParam(value = "decision", required = true) Long examinerListingId, Model model, Principal principal, HttpServletRequest request) {
			log.debug("viewExaminers, model "+ReflectionToStringBuilder.toString(model, ToStringStyle.MULTI_LINE_STYLE));

       try {

//			List<ExaminersList> el = elRepo.findAll();
//			List<Examiner> exs = exRepo.findAvailable();
//			log.debug("Examines: " + exs);

			Map<EduBoard,List<Examiner>> exMap = new HashMap<EduBoard,List<Examiner>>();
			ExaminersList el = elRepo.findById(examinerListingId).get();
			if (el instanceof ExaminersDecision) {
				exMap.put(((ExaminersDecision)el).getBoard(),exRepo.findByDecision(el));
				model.addAttribute("decisionDate", ((ExaminersDecision)el).getDecisionDate());	
				model.addAttribute("defaultExaminers", ((ExaminersDecision)el).getDefaultExaminers());
			} else {
				for (EduBoard board : EduBoard.values()) {
					exMap.put(board,exRepo.findAvailableByBoard(board));
					model.addAttribute("decisionDate", new Date());
					model.addAttribute("defaultExaminers", new ArrayList<String>());
				}	
			}
//			EconomyDocument edoc = emRepo.findByYear(year);

			
			model.addAttribute("serverTime", new Date());
//			model.addAttribute("edoc", edoc);
//			model.addAttribute("staff", staffService);
			model.addAttribute("staffMap", staffService.getDesignatedExaminers());
			model.addAttribute("examiners", exMap);
			model.addAttribute("boards", exMap.keySet());

    		return "Examiners";
        } catch (Exception e) {
			log.error("viewExaminers, caught a pesky exception "+ e);
			return "{\"ERROR\":"+e.getMessage()+"\"}";
		}
	}

    @RequestMapping(value = "/view/deptexaminers", method = RequestMethod.GET)
    public String viewDepartmentExaminers(@RequestParam(value = "decision", required = true) Long examinerListingId, Model model, Principal principal, HttpServletRequest request) {
			log.debug("viewDepartmentExaminers, model "+ReflectionToStringBuilder.toString(model, ToStringStyle.MULTI_LINE_STYLE));

       try {


//			Map<String,List<Examiner>> deptMap = new HashMap<String,List<Examiner>>();
			ExaminersList el = elRepo.findById(examinerListingId).get();
			List<Examiner> examiners = new ArrayList<Examiner>(); 
			Map<String, Staff> staffMap = staffService.getDesignatedExaminers();
			
			
			if (el instanceof ExaminersDecision) {
				examiners = exRepo.findByDecision(el);
				model.addAttribute("decisionDate", ((ExaminersDecision)el).getDecisionDate());	
			} else {
				for (EduBoard board : EduBoard.values()) {
					examiners.addAll(exRepo.findAvailableByBoard(board));
					model.addAttribute("decisionDate", new Date());
				}	
			}
         		
         	List<DeptExaminer> deptExaminers = examiners.stream()
				.map(examiner -> {
					DeptExaminer dex = new DeptExaminer(); // Convert examiner to DeptExaminer
					dex.setExaminer(examiner);
					dex.setStaff(staffMap.get(examiner.getExaminer()));
					return dex;
				})
				.collect(Collectors.toList());

         	deptExaminers.sort(Comparator.comparing(DeptExaminer::getDepartment).thenComparing(DeptExaminer::getName));
   	
   			
			model.addAttribute("serverTime", new Date());
//			model.addAttribute("staffMap", staffMap);
			model.addAttribute("examiners", deptExaminers);
// 			model.addAttribute("boards", exMap.keySet());

    		return "DepartmentExaminers";
        } catch (Exception e) {
			log.error("viewDepartmentExaminers, caught a pesky exception "+ e);
			return "{\"ERROR\":"+e.getMessage()+"\"}";
		}
	}

    @RequestMapping("/excel/examiners")
    public ModelAndView viewExaminersExcelDoc(@RequestParam(value = "decision", required = true) Long examinerListingId, Principal principal, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        Map<String, String> matchMap = new HashMap<String, String>();

		ExaminersList el = elRepo.findById(examinerListingId).get();
		List<Examiner> examiners = exRepo.findByDecision(el);
		Map<Course,List<Examiner>> exMap = new HashMap<Course,List<Examiner>>();
		
		if (el instanceof ExaminersDecision) {
			model.put("decisionDate", ((ExaminersDecision)el).getDecisionDate());	
			model.put("defaultExaminers", ((ExaminersDecision)el).getDefaultExaminers());
			model.put("board",((ExaminersDecision)el).getBoard());
		} else {
			model.put("decisionDate", new Date());
			model.put("defaultExaminers", new ArrayList<String>());
			model.put("board","någon");
		}

		/* This is a hack to compare with the previous decision to tag differences */	
		if (el instanceof ExaminersDecision) {
			log.debug( "Board: " +  ((ExaminersDecision)el).getBoard().name() );
			log.debug( "Date: " + ((ExaminersDecision)el).getDecisionDate() );
			ExaminersList pel = elRepo.findPreceeding( ((ExaminersDecision)el).getBoard().name(), ((ExaminersDecision)el).getDecisionDate() );
			log.debug( "The previous decision: " +  pel);

			List<Object[]> courseDiffList;
			if (pel == null) {
				courseDiffList= exRepo.compareELists(el.getId(), el.getId());
			} else {
				courseDiffList= exRepo.compareELists(el.getId(), pel.getId());
			} 
			for (Object[] ob : courseDiffList){
				String key = (String)ob[0];
				BigInteger value = (BigInteger)ob[1];
				switch (value.intValue()) {
					case 1: matchMap.put(key,DiffKind.CHANGE.toString());break;
					case 2: matchMap.put(key,DiffKind.NEW.toString());break;
					case 3: matchMap.put(key,DiffKind.DELETE.toString());break;
					default: matchMap.put(key,DiffKind.NONE.toString());
				}
				
			}
		} else {
			ExaminersList pel;
			List<Object[]> courseDiffList = new ArrayList<Object[]>();
			for(EduBoard theBoard : EduBoard.values()) {
				pel = elRepo.findPreceeding( theBoard.name(), new Date() );
				if (pel != null) {
					courseDiffList.addAll( exRepo.compareELists( el.getId(), pel.getId(), theBoard.name() ) );
					log.debug( "The previous decision: " +  pel);
				}
			}
			for (Object[] ob : courseDiffList) {
				String key = (String)ob[0];
				BigInteger value = (BigInteger)ob[1];
				log.debug( "The comparison: " +  key + ": " + value);
				switch (value.intValue()) {
					case 1: matchMap.put(key,DiffKind.CHANGE.toString());break;
					case 2: matchMap.put(key,DiffKind.NEW.toString());break;
					case 3: matchMap.put(key,DiffKind.DELETE.toString());break;
					default: matchMap.put(key,DiffKind.NONE.toString());
				}
			}
		}

		for (Examiner ex : examiners) {
			if (!exMap.containsKey(ex.getCourse())) {
				exMap.put(ex.getCourse(), new ArrayList<Examiner>());
			} 
			exMap.get(ex.getCourse()).add(ex);			
		}

        model.put("exMap", exMap);
        model.put("matchMap", matchMap);
        model.put("exList", el);
        model.put("examiners", examiners);
		model.put("staffMap", staffService.getDesignatedExaminers());
        model.put("sheetname", "Examinatorer, IBGs kurser, beslut av " + model.get("board"));

        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Kurskod");
        headers.add("Kursnamn");
        headers.add("Examinatorer");
        headers.add("Kommentar");

        model.put("headers", headers);

        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=" + "Examinatorslista-" + model.get("board") + ".xls" );
        return new ModelAndView(new ExaminersExcel(), model);
    }


	/* EconomyDocument views */
	
    @RequestMapping(value = "/view/adjustmentdoc", method = RequestMethod.GET)
    public String viewAdjustmentDoc(@RequestParam(value = "year", required = true) Integer year, Model model, Principal principal, HttpServletRequest request) {
			log.debug("viewAdjustmentDoc, model "+ReflectionToStringBuilder.toString(model, ToStringStyle.MULTI_LINE_STYLE));
       try {
			Map<CourseGroup, Collection<CourseInstance>> ciMap = new HashMap<CourseGroup, Collection<CourseInstance>>();
			EconomyDocument edoc = emRepo.findByYear(year);

for (CourseInstance ci : edoc.getBalancedCourseInstances()) {
	log.debug("mappedAdjustments " + edoc.getYear() + ", " + ci.mapAccumulatedGrantAdjustment());
}

//			log.debug("Document " + edoc.getYear() +", " + edoc.sumByCourseGroup());

			for (CourseInstance ci : edoc.getCourseInstances()) {
				if (!ciMap.containsKey(ci.getCourse().getCourseGroup())) {
					ciMap.put(ci.getCourse().getCourseGroup(), new HashSet<CourseInstance>());
				}
				ciMap.get(ci.getCourse().getCourseGroup()).add(ci);
			}
			log.debug("viewAdjustmentDoc, course instances done");

			for (CourseGroup cgrp : ciMap.keySet()) {
				ciMap.put(cgrp, asSortedList(ciMap.get(cgrp)));
			}
			log.debug("viewAdjustmentDoc, course groups done");

			model.addAttribute("serverTime", new Date());
			model.addAttribute("edoc", edoc);
			model.addAttribute("courseInstances", ciMap);
			model.addAttribute("usedGroups", asSortedList(ciMap.keySet()));
			model.addAttribute("sums", new TableSum());
			model.addAttribute("models", fmRepo.findDistinctByEconDoc(edoc));
			emRepo.save(edoc);

    		return "EconomyDocAdjustmentView";
        } catch (Exception e) {
			log.error("viewEconomyDoc, caught a pesky exception "+ e);
			return "{\"ERROR\":"+e.getMessage()+"\"}";
		}
	}
	

    @RequestMapping(value = "/view/economydoc", method = RequestMethod.GET)
    public String viewEconomyDoc(@RequestParam(value = "year", required = true) Integer year, Model model, Principal principal, HttpServletRequest request) {
			log.debug("viewEconomyDoc, model "+ReflectionToStringBuilder.toString(model, ToStringStyle.MULTI_LINE_STYLE));

       try {
//			Map<CourseGroup, CourseInstance> ciMap = new HashMap<CourseGroup, CourseInstance>();
			Map<CourseGroup, Collection<CourseInstance>> ciMap = new HashMap<CourseGroup, Collection<CourseInstance>>();
			EconomyDocument edoc = emRepo.findByYear(year);

			log.debug("Document " + edoc.getYear() +", " + edoc.sumByCourseGroup());

			for (CourseInstance ci : edoc.getCourseInstances()) {
//				ciMap.put(ci.getCourse().getCourseGroup(), ci);
				if (!ciMap.containsKey(ci.getCourse().getCourseGroup())) {
					ciMap.put(ci.getCourse().getCourseGroup(), new HashSet<CourseInstance>());
				}
				ciMap.get(ci.getCourse().getCourseGroup()).add(ci);
			}
			log.debug("viewEconomyDoc, course instances done");

			for (CourseGroup cgrp : ciMap.keySet()) {
				ciMap.put(cgrp, asSortedList(ciMap.get(cgrp)));
			}
			log.debug("viewEconomyDoc, course groups done");

			model.addAttribute("serverTime", new Date());
			model.addAttribute("edoc", edoc);
			model.addAttribute("courseInstances", ciMap);
			model.addAttribute("usedGroups", asSortedList(ciMap.keySet()));
			model.addAttribute("sums", new TableSum());
			model.addAttribute("models", fmRepo.findDistinctByEconDoc(edoc));
			emRepo.save(edoc);

    		return "EconomyDocView";
//    		return "EconomyDocAdjustmentView";
        } catch (Exception e) {
			log.error("viewEconomyDoc, caught a pesky exception "+ e);
			return "{\"ERROR\":"+e.getMessage()+"\"}";
		}
	}

    @RequestMapping("/excel/economydoc")
    public ModelAndView viewEconomyExcelDoc(@RequestParam(value = "year", required = true) Integer year, Principal principal, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
		EconomyDocument edoc = emRepo.findByYear(year);

        model.put("edoc", edoc);
        model.put("sheetname", "Kursekonomidokument-" + edoc.getYear());

        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Kurs");
        headers.add("hp");
        headers.add("Stud.");
        headers.add("Reg.");
        headers.add("åp");
        headers.add("Modell");
        headers.add("Ny");
        headers.add("Anslag");
        for (Department dept : edoc.getAccountedDeptsSorted()) {
        	headers.add(dept.toString());
        }
        for (Department dept : edoc.getAccountedDeptsSorted()) {
        	headers.add(dept.toString());
        }
        model.put("headers", headers);

        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=" + "Kursekonomidokument-" + edoc.getYear() + ".xls" );
        return new ModelAndView(new EconomyDocExcel(), model);
    }


    @RequestMapping(value = "/view/adjusted", method = RequestMethod.GET)
    public String viewAdjusedtDoc(@RequestParam(value = "year", required = true) Integer year, Model model, Principal principal, HttpServletRequest request) {
			log.debug("viewAdjustmentDoc, model "+ReflectionToStringBuilder.toString(model, ToStringStyle.MULTI_LINE_STYLE));
       try {
			Map<CourseGroup, Collection<CourseInstance>> ciMap = new HashMap<CourseGroup, Collection<CourseInstance>>();
			EconomyDocument edoc = emRepo.findByYear(year);

for (CourseInstance ci : edoc.getBalancedCourseInstances()) {
	log.debug("mappedAdjustments " + edoc.getYear() + ", " + ci.mapAccumulatedGrantAdjustment());
}

//			log.debug("Document " + edoc.getYear() +", " + edoc.sumByCourseGroup());

			for (CourseInstance ci : edoc.getBalancedCourseInstances()) {
				if (!ciMap.containsKey(ci.getCourse().getCourseGroup())) {
					ciMap.put(ci.getCourse().getCourseGroup(), new HashSet<CourseInstance>());
				}
				ciMap.get(ci.getCourse().getCourseGroup()).add(ci);
			}
			log.debug("viewAdjustmentDoc, course instances done");

			for (CourseGroup cgrp : ciMap.keySet()) {
				ciMap.put(cgrp, asSortedList(ciMap.get(cgrp)));
			}
			log.debug("viewAdjustmentDoc, course groups done");

			model.addAttribute("serverTime", new Date());
			model.addAttribute("edoc", edoc);
			model.addAttribute("courseInstances", ciMap);
			model.addAttribute("usedGroups", asSortedList(ciMap.keySet()));
			model.addAttribute("sums", new TableSum());
			model.addAttribute("models", fmRepo.findDistinctByEconDoc(edoc));
			emRepo.save(edoc);

    		return "EconomyDocAdjustmentView";
        } catch (Exception e) {
			log.error("viewEconomyDoc, caught a pesky exception "+ e);
			return "{\"ERROR\":"+e.getMessage()+"\"}";
		}
	}

	/* Courses by programme listing */

	@RequestMapping("/view/programcourses")
    public String getCoursesByProgrammes(Model model) {

/*
		TBM1K&pInr=BIOL	Kandidatprogram i biologi/molekylärbiologi
		TBM1K&pInr=BIKG	Kandidatprogram i biologi/molekylärbiologi, ingång för samhällsvetare
		TMV1K			Kandidatprogram i miljövetenskap

		TTB2M			Masterprogram i tillämpad bioteknik
		TBK2M&pInr=BIOL	Masterprogram i bioinformatik, biologiingång
		TBK2M&pInr=DATA	Masterprogram i bioinformatik, dataingång
		TBI2M			Masterprogram i biologi
		TMB2M			Masterprogram i molekylär bioteknik

		TMB2Y			Civilingenjörsprogrammet i molekylär bioteknik
		TMV2Y			Civilingenjörsprogrammet i miljö- och vattenteknik
		UGY2Y			Ämneslärarprogrammet med inriktning mot arbete i gymnasieskolan
*/

		Map<String,ProgCourse> courseMap = new HashMap<String,ProgCourse>();
        List<String> programmes = Arrays.asList("TTB2M", "TMB2Y", "TMV2Y", "TBM1K&pInr=BIOL", "TBM1K&pInr=BIKG", "TMV1K", "TBK2M&pInr=BIOL", "TBK2M&pInr=DATA", "TBI2M", "TMB2M", "UGY2Y");

		for (String pKod : programmes) {
			log.debug("pKod: "+ pKod);
			for (String kKod : findCoursesInSP(pKod)) {
				log.debug("kKod: "+ kKod);
				if (!courseMap.containsKey(kKod)) {
					ProgCourse pc = new ProgCourse();
					Course c = courseRepo.findByCode(kKod);
					if (c == null) {c = new Course();}
					pc.setCourse(c);
					courseMap.put(kKod, pc );
				}
				courseMap.get(kKod).getProgrammes().add(pKod);
			}
		}

		List<String> courseList = new ArrayList<String>();
		courseList.addAll(courseMap.keySet());
		Collections.sort(courseList);

        model.addAttribute("serverTime", new Date());
        model.addAttribute("programmes", programmes);
        model.addAttribute("courses", courseMap);
        model.addAttribute("courseList", courseList);
        return "ProgramCourseSummary";
    }



	private List<String> findCoursesInSP(String pKod) {
		String kKodPattern = ".*kKod=(\\d\\w{2}\\d{3}).*";
//		String luntanCode = "(1BG|1MB|1DL|1KB|8BL|1GV|1BL|3MK)\\d{3}";
		String luntanCode = "(1BG|1MB|8BL|1BL)\\d{3}";
		Pattern p = Pattern.compile(luntanCode);
		List<String> courses = new ArrayList<String>();
		try {
			Document doc = Jsoup.connect("https://www.uu.se/utbildning/utbildningar/selma/studieplan/?pKod="+pKod).get();
			log.debug("Program: "+ doc.title());
			Elements links = doc.select("a[href*=kKod]");
			for (Element el : links) {
				String kKod = el.toString().replaceAll(kKodPattern, "$1");
//				courses.add(kKod);
				if (p.matcher(kKod).matches()) {
					courses.add(kKod);
				}
				log.debug("Found: "+ kKod + "\t" + pKod);
			}
		} catch (Exception e) {
			log.error("Got a pesky exception, "+e);
		} finally {
			return courses;
		}
    }

	/**
	 * <p>The method asSortedList returns a sorted list from a collection of objects
	 * </p>
	 * @param c is a collection of objects
	 * @return is a sorted list of objects
	 * @since 1.0
	 */
	private static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
		List<T> list = new ArrayList<T>(c);
		java.util.Collections.sort(list);
		return list;
	}


	private class TableSum {
		public Map<CourseGroup, Float> colSum(Map<CourseGroup,Map<Department,Float>> table){
			Map<CourseGroup, Float> csum = new HashMap<CourseGroup, Float>();
			for (CourseGroup cg : table.keySet()) {
				Float s = 0.0f;
				for (Float f : table.get(cg).values()) {
					s += f;
				}
				csum.put(cg,s);
			}
			log.debug("csum " + csum);
			return csum;
		}
		public Map<Department, Float> rowSum(Map<CourseGroup,Map<Department,Float>> table){
			Map<Department, Float> rsum = new HashMap<Department, Float>();
			for (CourseGroup cg : table.keySet()) {
				for (Department dep : table.get(cg).keySet()){
					if (rsum.containsKey(dep)) {
						rsum.put(dep,table.get(cg).get(dep) + rsum.get(dep));
					} else {
						rsum.put(dep,table.get(cg).get(dep));
					}
				}
			}
			log.debug("rsum " + rsum);
			return rsum;
		}
		public Float totalSum(Map<Object, Float> arr) {
			Float s = 0.0f;
			for (Float f : arr.values()) {
				s+=f;
			}
			return s;
		}
	}

	@Data
	private class ProgCourse {
		private Course course;
		private Set<String> programmes = new HashSet<String>();

		public String inProg(String pKod) {
			return programmes.contains(pKod) ? "X" : "";
		}
	}

	@Data
	private class DeptExaminer {
		private Staff staff;
		private Examiner examiner;
	
		public String getDepartment() {
			return staff.getFullDepartment();
		}
		public String getName() {
			return staff.getSortingName();
		}
		public String getCourseCode() {
			return examiner.getCourse().getCode();
		}
	}

/*
	@RequestMapping(value="rest/test", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> test() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("test").transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate").deepSerialize(fmRepo.slask()), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
 */

}
