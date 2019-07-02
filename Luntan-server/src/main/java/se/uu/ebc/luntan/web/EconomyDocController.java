package se.uu.ebc.luntan.web;
 
 
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import flexjson.transformer.NullTransformer;
//import se.uu.ebc.bemanning.util.DateNullTransformer;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.security.Principal;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import se.uu.ebc.luntan.repo.EconomyDocumentRepo;
import se.uu.ebc.luntan.repo.FundingModelRepo;
import se.uu.ebc.luntan.service.EconomyDocumentService;
import se.uu.ebc.luntan.service.FundingModelService;
import se.uu.ebc.luntan.entity.CourseInstance;
import se.uu.ebc.luntan.entity.EconomyDocument ;
import se.uu.ebc.luntan.entity.FundingModel ;
import se.uu.ebc.luntan.enums.CourseGroup;
import se.uu.ebc.luntan.enums.Department;
import se.uu.ebc.luntan.vo.EconomyDocVO;

@Controller
@RequestMapping(value = "/")
@CrossOrigin(origins = "http://localhost:1841", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class EconomyDocController {

    private Logger log = Logger.getLogger(EconomyDocController.class.getName());

	@Autowired
	EconomyDocumentRepo emRepo;

	@Autowired
	FundingModelRepo fmRepo;

	@Autowired
	FundingModelService fmService;

	@Autowired
	EconomyDocumentService edService;
	
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
 
//	@PreAuthorize("hasRole('ROLE_COREDATAADMIN')")
    @RequestMapping(value="rest/fundingmodels/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateCourse(@RequestBody String json, @PathVariable("id") Long id) {
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

 
//	@PreAuthorize("hasRole('ROLE_COREDATAADMIN')")
    @RequestMapping(value="rest/fundingmodels", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createCourse(@RequestBody String json, UriComponentsBuilder uriBuilder) {
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


//	@PreAuthorize("hasRole('ROLE_COREDATAADMIN')")
	@RequestMapping(value = "rest/fundingmodels/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteCourse(@PathVariable("id") Long id) {
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

//	@PreAuthorize("hasRole('ROLE_COREDATAADMIN')")
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


    @RequestMapping(value = "/view/economydoc", method = RequestMethod.GET)
    public String viewEconomyDoc(@RequestParam(value = "year", required = true) Integer year, Model model, Principal principal, HttpServletRequest request) {
			log.debug("viewEconoomy, model "+ReflectionToStringBuilder.toString(model, ToStringStyle.MULTI_LINE_STYLE));

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
			for (CourseGroup cgrp : ciMap.keySet()) {
				ciMap.put(cgrp, asSortedList(ciMap.get(cgrp)));
			}
			model.addAttribute("serverTime", new Date());
			model.addAttribute("edoc", edoc);
			model.addAttribute("courseInstances", ciMap);
			model.addAttribute("usedGroups", asSortedList(ciMap.keySet()));
			model.addAttribute("sums", new TableSum());
			model.addAttribute("models", fmRepo.findDistinctByEconDoc(edoc));
			emRepo.save(edoc);
			
    		return "EconomyDocView";
        } catch (Exception e) {
			log.error("viewEconoomy, caught a pesky exception "+ e);
			return "{\"ERROR\":"+e.getMessage()+"\"}";
		}
	}


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
