package  se.uu.ebc.luntan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToMany;
// import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
// import jakarta.persistence.UniqueConstraint;
// import jakarta.persistence.Embedded;
// import jakarta.persistence.Embeddable;
// import jakarta.persistence.ElementCollection;
// import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
// import jakarta.persistence.MapKeyEnumerated;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.EnumType;


import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import se.uu.ebc.luntan.enums.*;
// import se.uu.ebc.luntan.aux.GrantMaps;
// 
// import jakarta.naming.Name;
// import jakarta.persistence.IdClass;

@Slf4j
@Data
@Entity
@Table(name = "EXTERNAL_TEACHER")
public class ExternalTeacher extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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