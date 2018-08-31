package se.uu.ebc.luntan.security;

import javax.persistence.Column;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import se.uu.ebc.luntan.enums.UserRoleType;

 @Entity
 @Table(name = "USER_ROLE")
 public class UserRole {

    @ManyToOne
    @JoinColumn(name = "user_fk")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "ROLE", length = 255)
    @NotNull
	@Enumerated(EnumType.STRING)    
    private UserRoleType role;
    
    public UserRoleType getRole() {
        return role;
    }
    
    public void setRole(UserRoleType role) {
        this.role = role;
    }


    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}
