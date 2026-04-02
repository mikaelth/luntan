package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;


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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "LINKID")
    @NotNull
    private String linkId;

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
