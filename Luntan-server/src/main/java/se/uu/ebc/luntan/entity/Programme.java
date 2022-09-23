package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import org.hibernate.annotations.GenericGenerator;

import se.uu.ebc.luntan.enums.CourseGroup;
import se.uu.ebc.luntan.enums.EduBoard;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PROGRAMME", uniqueConstraints= @UniqueConstraint(name = "UniqueProgramAndDirection",columnNames={"CODE","DIRECTION"}))
public class Programme extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ID")
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "CODE")
    @NotNull
    private String code;

    @Column(name = "DIRECTION")
    private String direction;

	@Column(name = "SE_NAME")
    @NotNull
    private String seName;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "INACTIVE")
    @NotNull
    private boolean inactive = false;

	@Column(name = "PD")
//    @NotNull
    private String programDirector;

 	/* Constructors */


	/* Setters and getters */


    /* Business methods */


    public String getDesignation() {
    	return this.code + " " + this.seName;
    }

	public String getSELMAPath () {
	
		return (this.direction == null || this.direction.equals("")) ? this.code : this.code + "&pInr=" + direction;
		
	}
}
