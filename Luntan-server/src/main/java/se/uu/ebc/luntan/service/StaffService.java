package se.uu.ebc.luntan.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.NamingException;
import javax.naming.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

import lombok.extern.slf4j.Slf4j;

import se.uu.ebc.luntan.repo.CourseRepo;
import se.uu.ebc.luntan.entity.ExternalTeacher;
import se.uu.ebc.luntan.repo.ExternalTeacherRepo;
import se.uu.ebc.luntan.repo.ExaminerRepo;

import se.uu.ebc.ldap.Staff;

@Service
@Slf4j
public class StaffService {

    @Autowired
	LdapTemplate ldapTemplate;

    @Autowired
	ExternalTeacherRepo externalsRepo;

    @Autowired
	ExaminerRepo examinerRepo;

	private final String BASE_DN = "cn=People,dc=uu,dc=se";


   public Staff findStaff(String dn) {
      return ldapTemplate.lookup(dn, new StaffAttributesMapper());
   }

   public Staff findbyEmployeeNumber (String employeeNumber) {
      return ldapTemplate.lookup(LdapNameBuilder.newInstance(BASE_DN).add("employeeNumber", employeeNumber).build(), new StaffAttributesMapper());
   }

    public List<Staff> getTeachersBiology () {
		List<Staff> theStaff = getBiologyTeachers();
		theStaff.addAll(getOtherDeptsTeachers());
		return theStaff;
	}
	
	@Cacheable("examinermap")
	public Map<String, Staff> getDesignatedExaminers() {
		Map<String, Staff> staffMap = new HashMap<String, Staff>();
		Set<String> designatedStaff = examinerRepo.findDesignatedLDAPEntries();
		for (String employeeNumber : designatedStaff) {
			staffMap.put(employeeNumber,findbyEmployeeNumber(employeeNumber));
		}
		return staffMap;
	}

    private List<Staff> getBiologyTeachers () {

        LdapQuery query = query()
        		.base(BASE_DN)
                .where("objectclass").is("person")
                .and(query()
                	.where("title").like("universitetslektor*")
                	.or("title").like("professor*")
                	.or("title").like("seniorprofessor*")
                	.or("title").like("biträdande universitetslektor*")
                	.or("title").like("forskarassistent*")
                	.or("title").like("adjunkt*")
                )
                .and(query()
                	.where("department").like("Institutionen för organismbiologi*")
                	.or("department").like("Institutionen för cell- och molekylärbiologi*")
                	.or("department").like("Institutionen för ekologi och genetik*")
 					.or("department").like("Institutionen för biologisk grundutbildning*")
				)
				.and(query()
					.where("title").not().like("professor emer*")
				);

        return ldapTemplate.search(query, new StaffAttributesMapper());
    }
 
     private List<Staff> getOtherDeptsTeachers () {

 		List<ExternalTeacher> teachers = externalsRepo.findAll();
		ContainerCriteria criteria = null;
		for (ExternalTeacher teacher : teachers) {
			if (criteria == null) {
				criteria = query().where("cn").is(teacher.getName()).and(query().where("ou").is(teacher.getDepartment()));
			} else {
				criteria = criteria.or(query().where("cn").is(teacher.getName()).and(query().where("ou").is(teacher.getDepartment())));
			}
		}
		
        LdapQuery query = query()
        		.base(BASE_DN)
                .where("objectclass").is("person")
                .and(query()
                	.where("title").like("universitetslektor*")
                	.or(query().where("title").like("professor*").and("title").not().like("professor emer*"))
                	.or("title").like("seniorprofessor*")
                	.or("title").like("biträdande universitetslektor*")
                	.or("title").like("forskarassistent*")
                	.or("title").like("adjunkt*")
                )
                .and(criteria);
		
 
        return ldapTemplate.search(query, new StaffAttributesMapper());
    }
 
 
    /**
     * Custom staff attributes mapper, maps the attributes to the staff POJO
     */
    private class StaffAttributesMapper implements AttributesMapper<Staff> {
        public Staff mapFromAttributes(Attributes attrs) throws NamingException {
            Staff person = new Staff();
 			try {
				person.setDn(LdapNameBuilder.newInstance(BASE_DN).add("employeeNumber", (String)attrs.get("employeeNumber").get()).build());
				person.setName((String)attrs.get("cn").get());
				person.setGivenName((String)attrs.get("givenname").get());
				person.setFamilyName((String)attrs.get("sn").get());
				person.setUsername((String)attrs.get("uid").get());
				person.setEmployeeNumber((String)attrs.get("employeeNumber").get());
			
				Attribute ou = attrs.get("ou");
				Attribute title = attrs.get("title");
				Attribute phone = attrs.get("telephonenumber");
				Attribute mail = attrs.get("mail");
				Attribute department = attrs.get("department");
			
				if (ou != null){
					person.setDepartment((String)ou.get());
				}
				if (title != null){
					person.setTitle((String)title.get());
				}
				if (phone != null){
					person.setPhone((String)phone.get());
				}
				if (mail != null){
					person.setMail((String)mail.get());
				}
				if (department != null){
					person.setFullDepartment((String)department.get());
				}

            } catch (Exception e) {
				log.error("Got a pesky exception: "  + e);
            } finally {
				log.debug("Find: " + person.toString());				
            	return person;
            }
            
        }
    }
 
 
}