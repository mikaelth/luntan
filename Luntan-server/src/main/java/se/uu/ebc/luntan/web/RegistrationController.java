package se.uu.ebc.luntan.web;

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
import se.uu.ebc.luntan.repo.CourseRepo;
import se.uu.ebc.luntan.repo.ProgrammeRepo;
import se.uu.ebc.luntan.repo.ExaminerRepo;
import se.uu.ebc.luntan.repo.EconomyDocumentRepo;
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

@Slf4j
@Controller
@RequestMapping(value = "/rest")
@CrossOrigin(origins = "http://localhost:1841", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class RegistrationController {


	private final String INACTIVE_STATEMENT = "Kursen är avvecklad";
	
	@Autowired
	CourseService courseService;

	@Autowired
	RegistrationService regService;

	@Autowired
	ExaminersService examinerService;

	@Autowired
	CourseRepo courseRepo;

	@Autowired
	ProgrammeRepo progRepo;

	@Autowired
	CourseInstanceRepo ciRepo;

	@Autowired
	EconomyDocumentRepo edRepo;

	@Autowired
	ExaminerRepo examinerRepo;

	@Autowired
	EconomyDocumentRepo edocRepo;



	/* Individual course registrations */
    @RequestMapping(value="/icrs", method = RequestMethod.GET)
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
    @RequestMapping(value="/icrs/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
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
    @RequestMapping(value="/icrs", method = RequestMethod.POST, headers = "Accept=application/json")
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

    @RequestMapping(value="/icts", method = RequestMethod.GET)
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
    @RequestMapping(value="/icts/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
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
    @RequestMapping(value="/icts", method = RequestMethod.POST, headers = "Accept=application/json")
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

    @RequestMapping(value="/iccbs", method = RequestMethod.GET)
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
    @RequestMapping(value="/iccbs/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
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
    @RequestMapping(value="/iccbs", method = RequestMethod.POST, headers = "Accept=application/json")
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


/* 
	Programmes
    @RequestMapping(value="/programmes", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> allProgrammes() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("programmes").transform(new DateTransformer("yyyy-MM-dd"), Date.class).deepSerialize(progRepo.findAll()), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

	@Secured({("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="/programmes/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateProgramme(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			ProgrammeVO cnew = new JSONDeserializer<ProgrammeVO>().use(null, ProgrammeVO.class).deserialize(json);
			cnew.setId(id);
			cnew = progService.saveProgramme(cnew);
 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("programmes").deepSerialize(cnew);
			restResponse = new StringBuilder(restResponse).insert(1, "'success': true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="/programmes", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createProgramme(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			ProgrammeVO cVO = new JSONDeserializer<ProgrammeVO>().use(null, ProgrammeVO.class).use(Date.class, new DateTransformer("yyyy-MM-dd") ).deserialize(json);
			cVO = progService.saveProgramme(cVO);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+cVO.getId().toString()).build().toUriString());

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("programmes").deepSerialize(cVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_SUBJECTCOORDINATOR")})
	@RequestMapping(value = "/programmes/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteProgramme(@PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			progService.deleteProgramme(id);
            return new ResponseEntity<String>("{success: true, id : " +id.toString() + "}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




	Courses
    @RequestMapping(value="/courses", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> allCourses() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("courses").transform(new DateTransformer("yyyy-MM-dd"), Date.class).deepSerialize(courseRepo.findAll()), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="/courses/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateCourse(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			Course cnew = new JSONDeserializer<Course>().use(null, Course.class).deserialize(json);
//			Course cold = courseRepo.findById(id);
			Course cold = courseRepo.findById(id).get();
			cold.copyProps(cnew);
			cnew = courseRepo.save(cold);

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("courses").deepSerialize(cnew);
			restResponse = new StringBuilder(restResponse).insert(1, "'success': true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="/courses", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createCourse(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			CourseVO cVO = new JSONDeserializer<CourseVO>().use(null, CourseVO.class).use(Date.class, new DateTransformer("yyyy-MM-dd") ).deserialize(json);
			cVO = courseService.saveCourse(cVO);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+cVO.getId().toString()).build().toUriString());

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("courses").deepSerialize(cVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
	@RequestMapping(value = "/courses/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteCourse(@PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			courseService.deleteCourse(id);
            return new ResponseEntity<String>("{success: true, id : " +id.toString() + "}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




	CourseInstances
    @RequestMapping(value="/cis", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> allCourseInstances() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("cis").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(courseService.getAllCourseInstances()), headers, HttpStatus.OK);
		} catch (Exception e) {
			log.debug("Caught a pesky exception in allCourseInstances() "+ e + ", " +e.getCause());
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR"),("ROLE_STAFFDIRECTOR")})
    @RequestMapping(value="/cis/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateCourseInstance(@RequestBody String json, @PathVariable("id") Long id, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			CourseInstanceVO ciVO = new JSONDeserializer<CourseInstanceVO>().use(null, CourseInstanceVO.class).use(Date.class, new DateNullTransformer("yyyy-MM-dd") ).deserialize(json);
			log.debug("updateCourseInstance, ciVO "+ReflectionToStringBuilder.toString(ciVO, ToStringStyle.MULTI_LINE_STYLE));
			log.debug("updateCourseInstance, grantDistribution "+ReflectionToStringBuilder.toString(ciVO.getGrantDistribution(), ToStringStyle.MULTI_LINE_STYLE));
			log.debug("updateCourseInstance, principal "+ReflectionToStringBuilder.toString(request.getUserPrincipal(), ToStringStyle.MULTI_LINE_STYLE));
			ciVO.setId(id);

			If role is STAFFDIRECTOR, only allow updating course leader			
			if (request.isUserInRole("ROLE_STAFFDIRECTOR")) {
				ciVO = courseService.saveCourseLeader(ciVO);
			} else {
				ciVO = courseService.saveCourseInstance(ciVO);
			}

			log.debug("updateCourseInstance, before serialize, ciVO "+ReflectionToStringBuilder.toString(ciVO, ToStringStyle.MULTI_LINE_STYLE));
			
 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("cis").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(ciVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.OK);
 
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="/cis", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createCourseInstance(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			CourseInstanceVO ciVO = new JSONDeserializer<CourseInstanceVO>().use(null, CourseInstanceVO.class).use(Date.class, new DateNullTransformer("yyyy-MM-dd") ).deserialize(json);
			log.debug("createCourseInstance, before save, ciVO "+ReflectionToStringBuilder.toString(ciVO, ToStringStyle.MULTI_LINE_STYLE));
			ciVO = courseService.saveCourseInstance(ciVO);
			log.debug("createCourseInstance, after save, ciVO "+ReflectionToStringBuilder.toString(ciVO, ToStringStyle.MULTI_LINE_STYLE));

            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+ciVO.getId().toString()).build().toUriString());

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("cis").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(ciVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.CREATED);
        } catch (Exception e) {
			log.error("createCourseInstance got a pesky exception: "+ e + e.getCause());
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
	@RequestMapping(value = "/cis/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteCourseInstance(@PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			courseService.deleteCourseInstance(id);
            return new ResponseEntity<String>("{success: true, id : " +id.toString() + "}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



	@Secured({("ROLE_REGISTRATIONUPDATER")})
	@RequestMapping(value = "/bulk/cibycsv", method = RequestMethod.GET)
    public String viewCSVCIUploadRequest(@RequestParam(value = "year", required = true) Integer year, Model model, HttpServletRequest request) {
		log.debug("viewCSVCIUploadRequest: " + ReflectionToStringBuilder.toString(model, ToStringStyle.MULTI_LINE_STYLE));		
		try {			
			CourseInstancesCSVUploadFB fb = new CourseInstancesCSVUploadFB();
  			fb.setYear(year);
			fb.setIgnoreExistingValues(false);
			
			model.addAttribute("years",edRepo.getYears());
 			model.addAttribute("formValues",fb);
			log.debug("viewCSVCIUploadRequest: " + ReflectionToStringBuilder.toString(model, ToStringStyle.MULTI_LINE_STYLE));		

			return "ViewCSVCIUpload";

        } catch (Exception e) {
				log.error("viewCSVCIUploadRequest, pesky exception "+e);
           return "{\"ERROR\":"+e.getMessage()+"\"}";        
        }
	}

	@Secured({("ROLE_REGISTRATIONUPDATER")})
	@RequestMapping(value="/bulk/requestUpdateRegsFromCSV", method = RequestMethod.POST, headers = "Accept=application/json")
    public String requestUpdateRegsFromCSV(Model model, HttpServletRequest request, HttpServletResponse response, final CourseInstancesCSVUploadFB formValues) {

		log.debug("FormValues: " + ReflectionToStringBuilder.toString(formValues, ToStringStyle.MULTI_LINE_STYLE));		
		log.debug("Model: " + ReflectionToStringBuilder.toString(model, ToStringStyle.MULTI_LINE_STYLE));
		log.debug("Request: " + ReflectionToStringBuilder.toString(request, ToStringStyle.MULTI_LINE_STYLE));
		
       // validate file
        if (formValues.getCiFile().isEmpty()) {
			log.error("No file received");
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {

// 			log.debug("Year: " + formValues.getYear());
//			log.debug("File: " + formValues.getCiFile().toString());
 			EconomyDocument edoc = edocRepo.findByYear(formValues.getYear());
 			
            // parse CSV file to create a list of `Specimen` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(formValues.getCiFile().getInputStream()))) {

                // create csv bean reader
                CsvToBean<LADOKEntryVO> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(LADOKEntryVO.class)
                        .withSeparator(';')
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSkipLines(10)
                        .build();

                // convert `CsvToBean` object to list of specimens
                List<LADOKEntryVO> cis = csvToBean.parse();
		
				Map<String, Long> counts = cis.stream().collect(Collectors.groupingBy(c -> c.getCourseCode(), Collectors.counting()));
				log.debug("Occurrences: " + counts);
				
				for (LADOKEntryVO ci : cis) {
					log.debug(ci.getCourseCode() + ", added ci " + ci.getInstanceCode() + " with " + ci.getRegistered() + " registered students; "+ci.getSize());

//					CourseInstance lci = ciRepo.findByInstanceCodeAndEconomyDoc(ci.getInstanceCode(), edoc);
					CourseInstance lci = ciRepo.findByInstanceCodeAndCourseCodeAndEconomyDoc(ci.getInstanceCode(), ci.getCourseCode(), edoc);
					log.debug("From instanceCode: " + ReflectionToStringBuilder.toString(lci, ToStringStyle.MULTI_LINE_STYLE));
					if (lci == null) {
						if ( counts.get(ci.getCourseCode()) > 1 ) {
							ci.setStatus(UpdateStatus.MULTIPLEQUERY);
						} else {
							List<CourseInstance> lcis = ciRepo.findByCourseCodeAndEconomyDoc(ci.getCourseCode(), edoc);
							log.debug("From courseCode " + lcis.size());
							if (lcis.size() == 0) {
								ci.setStatus(UpdateStatus.NOMATCH);
							} else if (lcis.size() > 1) {
								ci.setStatus(UpdateStatus.MULTIPLEMATCHES);
							} else {
								lci = lcis.get(0);
							}
						}
					}
					if (lci != null) {
						if (formValues.isIgnoreExistingValues() || lci.getRegisteredStudents() == null || lci.getRegisteredStudents() == 0) {
							ci.setPreviousValue(lci.getRegisteredStudents());
							lci.setRegisteredStudents(ci.getRegistered());
							lci.setRegistrationValid(true);
							ciRepo.save(lci);
							ci.setStatus(UpdateStatus.UPDATED);
						} else {
							ci.setStatus(UpdateStatus.EXISTINGVALUE);
							ci.setPreviousValue(lci.getRegisteredStudents());
						}
					}
					log.debug(ci.getCourseCode() + ", " + ci.getInstanceCode() + ", " + ci.getRegistered() + ", " + ci.getStatus());
				}

				model.addAttribute("serverTime", new Date());
				model.addAttribute("year", formValues.getYear());
				model.addAttribute("cis", cis);

			} catch (Exception ex) {
				log.error("requestUpdateRegsFromCSV caught a pesky exception: "+ ex);
				model.addAttribute("message", "An error occurred while processing the CSV file.");
				model.addAttribute("status", false);
			}
		}

        return "CIBulkUpdateOverview";
    }


	Examiners
    @RequestMapping(value="/examiners", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> allExaminers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("examiners").transform(new DateTransformer("yyyy-MM-dd"), Date.class).serialize(examinerService.getAllExaminers()), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

	@Secured({("ROLE_STAFFDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="/examiners/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateExaminer(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			ExaminerVO exVO = new JSONDeserializer<ExaminerVO>().use(null, ExaminerVO.class).use(Date.class, new DateNullTransformer("yyyy-MM-dd") ).deserialize(json);
			log.debug("updateExaminer, exVO "+ReflectionToStringBuilder.toString(exVO, ToStringStyle.MULTI_LINE_STYLE));
			exVO.setId(id);
			exVO = examinerService.saveExaminer(exVO);

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("examiners").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(exVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_STAFFDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="/examiners", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createExaminer(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			ExaminerVO exVO = new JSONDeserializer<ExaminerVO>().use(null, ExaminerVO.class).use(Date.class, new DateNullTransformer("yyyy-MM-dd") ).deserialize(json);
			log.debug("createExaminer, exVO "+ReflectionToStringBuilder.toString(exVO, ToStringStyle.MULTI_LINE_STYLE));
			exVO = examinerService.saveExaminer(exVO);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+exVO.getId().toString()).build().toUriString());

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("examiners").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(exVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_STAFFDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
	@RequestMapping(value = "/examiners/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteExaminer(@PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			examinerService.deleteExaminer(id);
            return new ResponseEntity<String>("{success: true, id : " +id.toString() + "}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@RequestMapping(value="/test", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> test() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {

			courseService.updateBalanceED();
			
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("test").transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate").deepSerialize("Done"), headers, HttpStatus.OK);
// 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("test").transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate").deepSerialize(examinerService.getAvailableByBoard(EduBoard.NUN)), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



	ExaminersLists
    @RequestMapping(value="/examinerslists", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> allExaminersLists() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
			log.debug("allExaminersLists, examiners lists "+ReflectionToStringBuilder.toString(examinerService.getAllExaminersLists(), ToStringStyle.MULTI_LINE_STYLE));
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("examinerslists").transform(new DateTransformer("yyyy-MM-dd"), Date.class).deepSerialize(examinerService.getAllExaminersLists()), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

	@Secured({("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="/examinerslists/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateExaminerList(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			ExListVO exVO = new JSONDeserializer<ExListVO>().use(null, ExListVO.class).use(Date.class, new DateNullTransformer("yyyy-MM-dd") ).deserialize(json);
			log.debug("updateExaminer, exVO "+ReflectionToStringBuilder.toString(exVO, ToStringStyle.MULTI_LINE_STYLE));
			exVO.setId(id);
			exVO = examinerService.updateExaminersDecision(exVO);

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("examinerslists").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(exVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="/examinerslists", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createExaminerList(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			ExListVO exVO = new JSONDeserializer<ExListVO>().use(null, ExListVO.class).use(Date.class, new DateNullTransformer("yyyy-MM-dd") ).deserialize(json);
			log.debug("createExaminerList, exVO "+ReflectionToStringBuilder.toString(exVO, ToStringStyle.MULTI_LINE_STYLE));
			exVO = examinerService.createExaminersDecision(exVO);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+exVO.getId().toString()).build().toUriString());

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("examinerslists").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(exVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_SUBJECTCOORDINATOR")})
	@RequestMapping(value = "/examinerslists/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteExaminerList(@PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			examinerService.deleteExaminersDecision(id);
            return new ResponseEntity<String>("{success: true, id : " +id.toString() + "}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@Secured({("ROLE_SUBJECTCOORDINATOR")})
    @RequestMapping(value="/courses/checkinactive", method = RequestMethod.GET)
    public ResponseEntity<String> checkInactiveCourses() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			List<Course> courses = courseRepo.findAll();

			for (Course course : courses) {
				if (!course.isInactive()) {
					Document doc = Jsoup.connect("https://www.uu.se/utbildning/utbildningar/selma/kursplan/?kKod="+course.getCode()).get();
					if (doc != null) {
						Elements syllabus = doc.select("ul.syllabusFacts");
						String theText = syllabus.text();
						log.debug("Syllabus text for "+ course.getCode() + ", " + theText);
						if (theText != null && theText.contains(INACTIVE_STATEMENT)) {
							course.setInactive(true);
							courseRepo.save(course);
						}
					}
				}
			}
			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("courses").transform(new DateTransformer("yyyy-MM-dd"), Date.class).deepSerialize(courses), headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	CourseGrants	Removed in v. 45
 */
}
