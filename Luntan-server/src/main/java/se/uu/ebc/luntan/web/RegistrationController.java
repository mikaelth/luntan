package se.uu.ebc.luntan.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;
import java.util.HashMap;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
//import se.uu.ebc.bemanning.util.DateNullTransformer;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

import javax.servlet.http.HttpServletRequest;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

//import org.apache.log4j.Logger;

import se.uu.ebc.luntan.service.CourseService;
import se.uu.ebc.luntan.service.ProgrammeService;
import se.uu.ebc.luntan.service.ExaminersService;
import se.uu.ebc.luntan.service.RegistrationService;

import se.uu.ebc.luntan.entity.Course;
import se.uu.ebc.luntan.entity.Programme;
import se.uu.ebc.luntan.entity.CourseInstance;
import se.uu.ebc.luntan.entity.EconomyDocument;
import se.uu.ebc.luntan.entity.IndividualCourseCreditBasis;
import se.uu.ebc.luntan.repo.CourseRepo;
import se.uu.ebc.luntan.repo.ProgrammeRepo;
import se.uu.ebc.luntan.repo.ExaminerRepo;
import se.uu.ebc.luntan.repo.EconomyDocumentRepo;
import se.uu.ebc.luntan.repo.IndividualCourseCreditBasisRepo;
import se.uu.ebc.luntan.util.DateNullTransformer;
import se.uu.ebc.luntan.vo.CourseInstanceVO;
import se.uu.ebc.luntan.vo.CourseVO;
import se.uu.ebc.luntan.vo.ProgrammeVO;
import se.uu.ebc.luntan.vo.ExaminerVO;
import se.uu.ebc.luntan.vo.ExListVO;
import se.uu.ebc.luntan.vo.IndCCBasisVO;
import se.uu.ebc.luntan.vo.CourseInstancesCSVUploadFB;
import se.uu.ebc.luntan.vo.LADOKEntryVO;
import se.uu.ebc.luntan.vo.IndRegVO;
import se.uu.ebc.luntan.vo.IndCourseTeacherVO;
import se.uu.ebc.luntan.enums.UpdateStatus;

import se.uu.ebc.luntan.enums.EduBoard;

import lombok.extern.slf4j.Slf4j;

import se.uu.ebc.luntan.repo.CourseInstanceRepo;
import se.uu.ebc.luntan.repo.EconomyDocumentRepo;
import se.uu.ebc.luntan.web.view.BillingExcelDoc;

@Slf4j
@Controller
@RequestMapping(value = "/")
@CrossOrigin(origins = "http://localhost:1841", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class RegistrationController {


	private final String INACTIVE_STATEMENT = "Kursen är avvecklad";
	
	@Autowired
	CourseService courseService;

	@Autowired
	RegistrationService regService;

/* 
	@Autowired
	ExaminersService examinerService;

	@Autowired
	CourseRepo courseRepo;

	@Autowired
	ProgrammeRepo progRepo;
 */

	@Autowired
	CourseInstanceRepo ciRepo;

	@Autowired
	IndividualCourseCreditBasisRepo bdocRepo;

	@Autowired
	ExaminerRepo examinerRepo;

	@Autowired
	EconomyDocumentRepo edocRepo;



	/* Individual course registrations */
    @RequestMapping(value="rest/icrs", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> allRegistrations() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("icrs").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(regService.getAllRegistrations()), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="rest/icrs/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateRegistration(@RequestBody String json, @PathVariable("id") Long id, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			IndRegVO ciVO = new JSONDeserializer<IndRegVO>().use(null, IndRegVO.class).use(Date.class, new DateNullTransformer("yyyy-MM-dd") ).deserialize(json);
			log.debug("updateRegistration, ciVO "+ReflectionToStringBuilder.toString(ciVO, ToStringStyle.MULTI_LINE_STYLE));
			log.debug("updateRegistration, principal "+ReflectionToStringBuilder.toString(request.getUserPrincipal(), ToStringStyle.MULTI_LINE_STYLE));
			ciVO.setId(id);

			ciVO = regService.saveRegistration(ciVO);

			log.debug("updateRegistration, before serialize, ciVO "+ReflectionToStringBuilder.toString(ciVO, ToStringStyle.MULTI_LINE_STYLE));
			
 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("icrs").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(ciVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.OK);
 
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="rest/icrs", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createRegistration(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			IndRegVO ciVO = new JSONDeserializer<IndRegVO>().use(null, IndRegVO.class).use(Date.class, new DateNullTransformer("yyyy-MM-dd") ).deserialize(json);
			ciVO = regService.saveRegistration(ciVO);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+ciVO.getId().toString()).build().toUriString());

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("icrs").deepSerialize(ciVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
	@RequestMapping(value = "/icrs/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteRegistration(@PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			regService.deleteRegistration(id);
            return new ResponseEntity<String>("{success: true, id : " +id.toString() + "}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	/* Individual course teachers, i.e., coordinators, supervisors and readers */

    @RequestMapping(value="rest/icts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> allICTeachers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("icts").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(regService.getAllICTeachers()), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="rest/icts/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateICTeacher(@RequestBody String json, @PathVariable("id") Long id, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			IndCourseTeacherVO ciVO = new JSONDeserializer<IndCourseTeacherVO>().use(null, IndCourseTeacherVO.class).use(Date.class, new DateNullTransformer("yyyy-MM-dd") ).deserialize(json);
			log.debug("updateICTeacher, ciVO "+ReflectionToStringBuilder.toString(ciVO, ToStringStyle.MULTI_LINE_STYLE));
			log.debug("updateICTeacher, principal "+ReflectionToStringBuilder.toString(request.getUserPrincipal(), ToStringStyle.MULTI_LINE_STYLE));
			ciVO.setId(id);

			ciVO = regService.saveICTeacher(ciVO);

			log.debug("updateICTeacher, before serialize, ciVO "+ReflectionToStringBuilder.toString(ciVO, ToStringStyle.MULTI_LINE_STYLE));
			
 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("icts").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(ciVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.OK);
 
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="rest/icts", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createICTeacher(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			IndCourseTeacherVO ciVO = new JSONDeserializer<IndCourseTeacherVO>().use(null, IndCourseTeacherVO.class).use(Date.class, new DateNullTransformer("yyyy-MM-dd") ).deserialize(json);
			ciVO = regService.saveICTeacher(ciVO);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+ciVO.getId().toString()).build().toUriString());

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("icts").deepSerialize(ciVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
	@RequestMapping(value = "/icts/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteICTeacher(@PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			regService.deleteICTeacher(id);
            return new ResponseEntity<String>("{success: true, id : " +id.toString() + "}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	/* Individual course credit bases */

    @RequestMapping(value="rest/iccbs", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> allICCBasis() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("iccbs").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(regService.getAllICCBasis()), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="rest/iccbs/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateICCBasis(@RequestBody String json, @PathVariable("id") Long id, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			IndCCBasisVO ciVO = new JSONDeserializer<IndCCBasisVO>().use(null, IndCCBasisVO.class).use(Date.class, new DateNullTransformer("yyyy-MM-dd") ).deserialize(json);
			log.debug("updateICCBasis, ciVO "+ReflectionToStringBuilder.toString(ciVO, ToStringStyle.MULTI_LINE_STYLE));
			log.debug("updateICCBasis, principal "+ReflectionToStringBuilder.toString(request.getUserPrincipal(), ToStringStyle.MULTI_LINE_STYLE));
			ciVO.setId(id);

			ciVO = regService.saveICCBasis(ciVO);

			log.debug("updateICCBasis, before serialize, ciVO "+ReflectionToStringBuilder.toString(ciVO, ToStringStyle.MULTI_LINE_STYLE));
			
 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("iccbs").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(ciVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.OK);
 
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="rest/iccbs", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createICCBasis(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			IndCCBasisVO ciVO = new JSONDeserializer<IndCCBasisVO>().use(null, IndCCBasisVO.class).use(Date.class, new DateNullTransformer("yyyy-MM-dd") ).deserialize(json);
			ciVO = regService.saveICCBasis(ciVO);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+ciVO.getId().toString()).build().toUriString());

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("iccbs").deepSerialize(ciVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
	@RequestMapping(value = "/iccbs/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteICCBasis(@PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			regService.deleteICCBasis(id);
            return new ResponseEntity<String>("{success: true, id : " +id.toString() + "}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @RequestMapping("excel/registrations")
    public ModelAndView viewECreditBasisExcel(@RequestParam(value = "billingdoc", required = true) Long docId, Principal principal, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();

        //Sheet Name
		IndividualCourseCreditBasis bdoc = bdocRepo.findById(docId).get();

        model.put("bdoc", bdoc);
        model.put("sheetname", "Betalningsunderlag-" + bdoc.getCreationDate());

        //Headers List
        List<String> headers = new ArrayList<String>();
        headers.add("Registreringsdatum");
        headers.add("Startdatum");
        headers.add("Kurs");
        headers.add("Student");
        headers.add("Koordinator");
        headers.add("Handledare");
        headers.add("Kommentar");
/* 
        for (Department dept : edoc.getAccountedDeptsSorted()) {
        	headers.add(dept.toString());
        }
        for (Department dept : edoc.getAccountedDeptsSorted()) {
        	headers.add(dept.toString());
        }
 */
        headers.add("Kommentar");


        model.put("headers", headers);

        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=" + "Betalningsunderlag-" + bdoc.getCreationDate() + ".xls" );
        return new ModelAndView(new BillingExcelDoc(), model);
    }


}
