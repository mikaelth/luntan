package se.uu.ebc.luntan.web;


import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.boot.info.BuildProperties;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import lombok.extern.slf4j.Slf4j;

import se.uu.ebc.luntan.security.UserRepo;

@Slf4j
@Controller
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:1841", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class IndexViewController {

//     private String roleArr[] = { "ROLE_DIRECTOROFSTUDIES", "ROLE_ADMINISTRATOR", "ROLE_PHDADMIN" };
//     private Set<String> rolesForAll = new HashSet(Arrays.asList(roleArr));


	@Autowired
	UserRepo userRepo;

	@Autowired
	BuildProperties buildProperties;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String indexPage(Model model, Principal principal, HttpServletRequest request) {

			log.debug("Build name "+buildProperties.getName());
			log.debug("Build version "+buildProperties.getVersion());
			log.debug("Build time "+buildProperties.getTime());
			log.debug("Build artifact "+buildProperties.getArtifact());

			log.debug("indexPage, model "+ReflectionToStringBuilder.toString(model, ToStringStyle.MULTI_LINE_STYLE));
//			log.debug("indexPage, principal "+ReflectionToStringBuilder.toString(principal, ToStringStyle.MULTI_LINE_STYLE));
// 			log.debug("indexPage, user "+ReflectionToStringBuilder.toString(userRepo.findUserByUsername(principal.getName()), ToStringStyle.MULTI_LINE_STYLE));

       try {
//			model.addAttribute("user", userRepo.findUserByUsername(principal.getName()));
    		return "EntryPage";
        } catch (Exception e) {
			log.error("indexPage, caught a pesky exception "+ e);
			return "{\"ERROR\":"+e.getMessage()+"\"}";
		}
	}



}
