package se.uu.ebc.luntan.security;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.springframework.format.annotation.DateTimeFormat;

import se.uu.ebc.luntan.security.UserRole;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "user")
    private Set<UserRole> userRoles;

    @Column(name = "USERNAME", length = 255)
    @NotNull
    private String username;
    
    @Column(name = "FIRST_NAME", length = 255)
    @NotNull
    private String firstName;
    
    @Column(name = "LAST_NAME", length = 255)
    @NotNull
    private String lastName;
    
    @Column(name = "PASSWORD", length = 255)
    @NotNull
    private String password;
    
    @Column(name = "EMAIL", length = 255)
    @NotNull
    private String email;
    
    @Column(name = "IS_ACTIVE")
    private Short isActive;
    
    @Column(name = "CREATION_DATE")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar creationDate;
    
    @Column(name = "COMMENT", length = 255)
    private String comment;
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Short getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }
    
    public Calendar getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("userRoles").toString();
    }

    public Set<UserRole> getUserRoles() {
        return this.userRoles;
    }
    
    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

}
