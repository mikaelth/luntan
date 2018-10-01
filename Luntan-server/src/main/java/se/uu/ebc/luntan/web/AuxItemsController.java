package se.uu.ebc.luntan.web;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import se.uu.ebc.luntan.enums.*;
import se.uu.ebc.luntan.repo.EconomyDocumentRepo;

import org.apache.log4j.Logger;

@RestController
@RequestMapping(value = "/rest")
@CrossOrigin(origins = "http://localhost:1841", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class AuxItemsController {

    private static Logger log = Logger.getLogger(AuxItemsController.class.getName());

	@Autowired
	EconomyDocumentRepo econDocRepo;

	@RequestMapping("/userroletypes")
    public List<Map<String, UserRoleType>> userRoleTypes() {
    	List theList = new ArrayList<Map<String, UserRoleType>>();
    	for (UserRoleType s : UserRoleType.values()) {
			java.util.Map<String,UserRoleType> vMap = new java.util.HashMap<String, UserRoleType>();
			vMap.put("label", s);
    		theList.add(vMap);
    	}
    	return theList;
    }
	@RequestMapping("/departments")
    public List<Map<String, Department>> departments() {
    	List theList = new ArrayList<Map<String, Department>>();
    	for (Department s : Department.values()) {
			java.util.Map<String,Department> vMap = new java.util.HashMap<String, Department>();
			vMap.put("label", s);
    		theList.add(vMap);
    	}
    	return theList;
    }

	@RequestMapping("/coursegroups")
    public List<Map<String, String>> courseGroup() {
    	List theList = new ArrayList<Map<String, String>>();
    	for (CourseGroup s : CourseGroup.values()) {
			java.util.Map<String,String> vMap = new java.util.HashMap<String, String>();
			vMap.put("label", s.toString());
    		theList.add(vMap);
    	}
    	return theList;
    }

	@RequestMapping("/cidesignations")
    public List<Map<String, String>> ciDesignation() {
    	List theList = new ArrayList<Map<String, String>>();
    	for (CIDesignation s : CIDesignation.values()) {
			java.util.Map<String,String> vMap = new java.util.HashMap<String, String>();
			vMap.put("label", s.toString());
    		theList.add(vMap);
    	}
    	return theList;
    }

	@RequestMapping("/years")
    public List<Map<String, String>> usedYears() {
    	List theList = new ArrayList<Map<String, String>>();
    	for (Integer s : econDocRepo.getYears()) {
			java.util.Map<String,String> vMap = new java.util.HashMap<String, String>();
			vMap.put("label", s.toString());
    		theList.add(vMap);
    	}
    	return theList;
    }

} 
