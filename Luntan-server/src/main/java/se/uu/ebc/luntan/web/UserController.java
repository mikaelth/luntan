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

import org.springframework.web.multipart.commons.CommonsMultipartFile;
 
import se.uu.ebc.luntan.security.UserRepo;
import se.uu.ebc.luntan.security.User;
import se.uu.ebc.luntan.enums.UserRoleType;
//import se.uu.ebc.luntan.service.PeopleService;
import se.uu.ebc.luntan.vo.UserVO;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

import java.security.Principal;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;

@Controller
@RequestMapping(value = "/rest")
@CrossOrigin(origins = "http://localhost:1841", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class UserController {

    private Logger logger = Logger.getLogger(UserController.class.getName());

	@Autowired
	UserRepo personRepo;


	/* Persons */
		
    @RequestMapping(value="/users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> allUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
 			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class","*.phDPosition","*.staff").rootName("users").transform(new DateTransformer("yyyy-MM-dd"), "updated").deepSerialize(this.getAllPersons()), headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
  	
    }
 
//	@PreAuthorize("hasRole('ROLE_COREDATAADMIN')")
    @RequestMapping(value="/users/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateUser(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			UserVO pVO = new JSONDeserializer<UserVO>().use(null, UserVO.class).deserialize(json);
			pVO.setId(id);
			pVO = this.savePerson(pVO);
			
 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("users").deepSerialize(pVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

 
//	@PreAuthorize("hasRole('ROLE_COREDATAADMIN')")
    @RequestMapping(value="/users", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createUser(@RequestBody String json, UriComponentsBuilder uriBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			UserVO pVO = new JSONDeserializer<UserVO>().use(null, UserVO.class).use(Date.class, new DateTransformer("yyyy-MM-dd") ).deserialize(json);
			pVO = this.savePerson(pVO);
            RequestMapping a = (RequestMapping) getClass().getAnnotation(RequestMapping.class);
            headers.add("Location",uriBuilder.path(a.value()[0]+"/"+pVO.getId().toString()).build().toUriString());

 			String restResponse = new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("users").deepSerialize(pVO);
			restResponse = new StringBuilder(restResponse).insert(1, "success: true,").toString();

            return new ResponseEntity<String>(restResponse, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//	@PreAuthorize("hasRole('ROLE_COREDATAADMIN')")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        try {
			this.deletePerson(id);
            return new ResponseEntity<String>("{success: true, id : " +id.toString() + "}", headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




	/* Curren user REST service */

	@Autowired
	UserRepo userRepo;
		
	@RequestMapping(value="/currentuser", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> loggedInUser(Principal principal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        try {
			logger.debug("loggedInUser... "+ ReflectionToStringBuilder.toString(principal, ToStringStyle.MULTI_LINE_STYLE));
			if (principal == null) { 
				// Dummy for testing purposes
     			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("currentuser").deepSerialize(createDummyUser()), headers, HttpStatus.OK);
			} else {
    			return new ResponseEntity<String>(new JSONSerializer().prettyPrint(true).exclude("*.class").rootName("currentuser").deepSerialize(new UserVO(userRepo.findUserByUsername(principal.getName()))), headers, HttpStatus.OK);
			}
		} catch (Exception e) {
           return new ResponseEntity<String>("{\"ERROR\":"+e.getMessage()+"\"}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
  	
    }



	private UserVO createDummyUser() {
		UserVO uvo = new UserVO();
		uvo.setId(0L);
		uvo.setUsername("anonymous");
		uvo.setFormName("Anonymous");
		uvo.setName("Anonymous");
		uvo.setFirstName("");
		uvo.setLastName("Anonymous");
		uvo.setUserRoles(new HashSet<UserRoleType>());
		uvo.getUserRoles().add(UserRoleType.Administrator);

		return uvo;
	}

	/* Auxilliary components */
	

	/* Person services */

	private List<UserVO> getAllPersons() throws Exception {
		List<UserVO> pVO = new ArrayList<UserVO>();
		try {	
			logger.debug("getAllPersons()");
			for (User p : personRepo.findAll()) {
 				pVO.add(new UserVO(p));
 			}
         	return pVO;        	        
        } catch (Exception e) {

			return null;
			
        }
    }
    
    private UserVO savePerson(UserVO pvo) throws Exception {
//    	User p = pvo.getId() == null ? toPerson(pvo) : toPerson(personRepo.findById(pvo.getId()), pvo);
    	User p = pvo.getId() == null ? toPerson(pvo) : toPerson(personRepo.findById(pvo.getId()).get(), pvo);
    	personRepo.save(p);
		return new UserVO(p);
    
    }

    private synchronized void deletePerson(Long pID) throws Exception {
//		User p = personRepo.findById(pID);
		User p = personRepo.findById(pID).get();
		personRepo.delete(p);
    }

   	
 
	private User toPerson (UserVO pvo) throws Exception {
 		return toPerson (new User(), pvo);
   	}

	private User toPerson (User p, UserVO pvo) throws Exception {


		try {
			p.setId(pvo.getId());
			p.setFirstName(pvo.getFirstName());
			p.setLastName(pvo.getLastName());
			p.setComment(pvo.getNote());
			p.setEmail(pvo.getEmail());
			p.setUsername(pvo.getUsername());
//			p.setIsActive(pvo.getIsActive());
//			p.setFamilyFirst(pvo.getFamilyFirst());
		
			p.setUserRoles(pvo.getUserRoles());		


		} catch (Exception e) {
			logger.error("toPerson got a pesky exception: "+ e + e.getCause());
		} finally {
			return p;
		}
	}
 
	
} 
