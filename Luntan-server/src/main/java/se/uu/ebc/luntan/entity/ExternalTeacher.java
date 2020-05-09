package  se.uu.ebc.luntan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
// import javax.persistence.OneToMany;
// import javax.persistence.OneToOne;
import javax.persistence.Table;
// import javax.persistence.UniqueConstraint;
// import javax.persistence.Embedded;
// import javax.persistence.Embeddable;
// import javax.persistence.ElementCollection;
// import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
// import javax.persistence.MapKeyEnumerated;
// import javax.persistence.Enumerated;
// import javax.persistence.EnumType;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import se.uu.ebc.luntan.enums.*;
// import se.uu.ebc.luntan.aux.GrantMaps;
// 
// import javax.naming.Name;
// import javax.persistence.IdClass;

@Slf4j
@Data
@Entity
@Table(name = "EXTERNAL_TEACHER")
public class ExternalTeacher extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

	@NotNull
	@Column(name = "NAME")
	private String name;

	@NotNull
	@Column(name = "DEPARTMENT")
	private String department;

	@Column(name = "NOTE")
	private String note;

}