package se.uu.ebc.luntan.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.NamingException;
import javax.naming.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

import lombok.extern.slf4j.Slf4j;

import se.uu.ebc.ldap.Staff;

@Service
@Slf4j
public class StaffService {
 
    @Autowired
	LdapTemplate ldapTemplate;

	private final String BASE_DN = "cn=People,dc=uu,dc=se";

    // business methods
 
/* 
 public List<String> search(Name dn) {
    return ldapTemplate
      .search(
		"",
        "dn=" + dn, 
        (AttributesMapper<String>) attrs -> (String) attrs.get("ou").get());
}

	public List<String> search(String username) {
		return ldapTemplate
		  .search(
			"",
			"uid=" + username, 
			(AttributesMapper<String>) attrs -> (String) attrs.get("ou").get());
	}

	public Staff findByEmployeeNumber(String employeeNumber) {
      LdapQuery query = query()
        		.base(BASE_DN)
                .where("objectclass").is("person")
                .and(query().where("employeeNumber").is(employeeNumber));

        return ldapTemplate.search(query, new UserAttributesMapper());
	}
 */

   public Staff findStaff(String dn) {
      return ldapTemplate.lookup(dn, new StaffAttributesMapper());
   }

   public Staff findbyEmployeeNumber (String employeeNumber) {
      return ldapTemplate.lookup(LdapNameBuilder.newInstance(BASE_DN).add("employeeNumber", employeeNumber).build(), new StaffAttributesMapper());
   }

    public List<Staff> getTeachersBiology () {

        LdapQuery query = query()
        		.base(BASE_DN)
                .where("objectclass").is("person")
                .and(query()
                	.where("title").is("universitetslektor")
                	.or("title").is("professor")
                	.or("title").is("biträdande universitetslektor")
                	.or("title").is("forskarassistent")
                	.or("title").is("adjunkt")
                	)
                .and(query()
                	.where("ou").is("Systematisk biologi")
                	.or("ou").is("Jämförande fysiologi")
                	.or("ou").is("Fysiologisk botanik")
                 	.or("ou").is("Miljötoxikologi")
                 	.or("ou").is("Människans evolution")
					.or("ou").is("Evolution och utvecklingsbiologi")

					.or("ou").is("Zooekologi")
					.or("ou").is("Växtekologi och evolution")
					.or("ou").is("Evolutionsbiologi")
					.or("ou").is("Limnologi")

					.or("ou").is("Beräkningsbiologi och bioinformatik")
					.or("ou").is("Mikrobiologi")
					.or("ou").is("Molekylär evolution")
					.or("ou").is("Strukturbiologi")
					.or("ou").is("Molekylärbiologi")
					.or("ou").is("Molekylär systembiologi")
					.or("ou").is("Molekylär biofysik")

					.or("ou").is("Institutionen för biologisk grundutbildning")
				);

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

            } catch (Exception e) {
				log.error("Got a pesky exception: "  + e);
            } finally {
				log.debug("Find: " + person.toString());				
            	return person;
            }
            
        }
    }
 
}