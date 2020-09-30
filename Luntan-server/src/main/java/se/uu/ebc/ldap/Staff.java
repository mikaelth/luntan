package se.uu.ebc.ldap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.DnAttribute;
import javax.naming.Name;
import java.util.StringJoiner;

import lombok.Data;

@Entry(
//  base = "cn=People,dc=uu,dc=se", 
//  base = "ou=Systematisk biologi", 
//  base = "title=universitetslektor", 
  base = "cn=People,dc=uu,dc=se", 
  objectClasses = { "person", "inetOrgPerson", "top", "uuAKKAperson", "eduPerson" })

@Data
public final class Staff {

    @Id
    private Name dn;

//    private @DnAttribute(value = "employeeNumber", index=0) String employeeNumber;
    private @Attribute(name = "employeeNumber")
    		@DnAttribute("employeeNumber") String employeeNumber;
    private @Attribute(name = "uid") String username;
	private @Attribute(name = "cn") String name;
	private @Attribute(name = "sn") String familyName;
	private @Attribute(name = "givenname") String givenName;
    private @Attribute(name = "title") String title;
    private @Attribute(name = "ou") String department;
    private @Attribute(name = "telephoneNumber") String phone;
    private @Attribute(name = "mail") String mail;
    private @Attribute(name = "department") String fullDepartment;
    private boolean examinerEligible;

	// Business methods
	
	public String getSortingName () {
		return familyName + ", " + givenName;
	}

	public String getNameAndOu () {
	
		StringJoiner staffJoiner = new StringJoiner(", ");
 
		staffJoiner.add(name);
		staffJoiner.add(title);		
		staffJoiner.add(fullDepartment);
		
		return staffJoiner.toString();
	} 
	
/* 
	public boolean isExaminerEligible() {
	
		return !title.toLowerCase().contains("forskare");
	}
 */
 }