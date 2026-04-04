
package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
//import java.util.Date;
import java.time.LocalDateTime;

import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;


import java.util.Set;
import java.util.HashSet;

import se.uu.ebc.luntan.enums.Department;
import se.uu.ebc.luntan.enums.CourseGroup;
import se.uu.ebc.luntan.aux.GrantMaps;

import lombok.extern.slf4j.Slf4j;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Singular;

@Slf4j
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CREDITBASIS")
public class IndividualCourseCreditBasis extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToMany(mappedBy = "creditBasisRecord")
    private Set<IndividualCourseRegistration> registrations = new HashSet<IndividualCourseRegistration>();

    @Column(name = "SENT")
    private LocalDateTime sent;

    @Column(name = "NOTE")
    private String note;

	/* Constructors */



}
