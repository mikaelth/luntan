package se.uu.ebc.luntan.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.annotation.Secured;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import flexjson.transformer.NullTransformer;
//import se.uu.ebc.bemanning.util.DateNullTransformer;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

//import org.apache.log4j.Logger;

import se.uu.ebc.luntan.service.CourseService;
import se.uu.ebc.luntan.service.ExaminersService;
import se.uu.ebc.luntan.service.StaffService;

import se.uu.ebc.luntan.entity.Course;
import se.uu.ebc.luntan.repo.CourseRepo;
import se.uu.ebc.luntan.repo.ExaminerRepo;
import se.uu.ebc.luntan.util.DateNullTransformer;
import se.uu.ebc.luntan.vo.CourseInstanceVO;
import se.uu.ebc.luntan.vo.CourseVO;
import se.uu.ebc.luntan.vo.ExaminerVO;
import se.uu.ebc.luntan.vo.ExListVO;

import se.uu.ebc.luntan.enums.EduBoard;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping(value = "/rest")
@CrossOrigin(origins = "http://localhost:1841", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class CourseController {

//    private Logger log = Logger.getLogger(CourseController.class.getName());


	@Autowired
	CourseService courseService;

	@Autowired
	ExaminersService examinerService;

	@Autowired
	CourseRepo courseRepo;

	@Autowired
	ExaminerRepo examinerRepo;

	/* Courses */

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




	/* CourseInstances */

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

			/* If role is STAFFDIRECTOR, only allow updating course leader */
			
			if (request.isUserInRole("ROLE_STAFFDIRECTOR")) {
				ciVO = courseService.saveCourseLeader(ciVO);
			} else {
				ciVO = courseService.saveCourseInstance(ciVO);
			}
			
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
			ciVO = courseService.saveCourseInstance(ciVO);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+ciVO.getId().toString()).build().toUriString());

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("cis").transform(new DateNullTransformer("yyyy-MM-dd"), Date.class).deepSerialize(ciVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.CREATED);
        } catch (Exception e) {
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


/* 
	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR")})
    private CourseInstanceVO updateEntireCourseInstance(CourseInstanceVO ciVO) {
		return courseService.saveCourseInstance(ciVO);

    }

	@Secured({("ROLE_COURSEDIRECTOR"),("ROLE_SUBJECTCOORDINATOR"),("ROLE_STAFFDIRECTOR")})
    private CourseInstanceVO updateCourseLeader(CourseInstanceVO ciVO) {
		return courseService.saveCourseLeader(ciVO);
    }
 */


	/* Examiners */

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
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("test").transform(new DateTransformer("yyyy-MM-dd"), "lastModifiedDate").deepSerialize(examinerService.getAvailableByBoard(EduBoard.NUN)), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



	/* ExaminersLists */

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


	/* CourseGrants */
	/* Removed in v. 45 */
}
