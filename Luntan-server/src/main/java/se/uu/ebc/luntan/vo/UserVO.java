package se.uu.ebc.luntan.vo;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import se.uu.ebc.luntan.security.User;
import se.uu.ebc.luntan.enums.UserRoleType;

import org.apache.log4j.Logger;

public class UserVO {

    
    private static Logger logger = Logger.getLogger(UserVO.class.getName());
	 
	private Long id;
	   
    private String username;
  	private String formName;
	private String name;
	private String firstName;
	private String lastName;
	private String email;
	private String note;

	private Set<UserRoleType> userRoles = new HashSet<UserRoleType>();

 	/* Setters and getters */
 	   

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
	public String getFormName()
	{
		return this.formName;
	}

	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}


	public String getNote()
	{
		return this.note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}


	public String getFirstName()
	{
		return this.firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}


	public String getLastName()
	{
		return this.lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}


	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}



	public Set<UserRoleType> getUserRoles()
	{
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRoleType> userRoles)
	{
		this.userRoles = userRoles;
	}
 
    
    /* Public methods */

  
 	/* Constructors */

	public UserVO (User p) {

		this.id = p.getId();
		
		this.username= p.getUsername();
		this.formName = p.getFormName();
		this.name = p.getName();
		this.firstName = p.getFirstName();
		this.lastName = p.getLastName();
		this.email = p.getEmail();
		this.note = p.getComment();
		this.userRoles = p.getUserRoles();
		
	}
	
	public UserVO() {}

}
