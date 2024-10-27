
package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import org.hibernate.annotations.GenericGenerator;

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
public class CreditBasis  extends Auditable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
	@GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ID")
    private Long id;
    
    @OneToMany(mappedBy = "creditBasisRecord")
    private Set<IndividualCourseRegistration> registrations = new HashSet<IndividualCourseRegistration>();    
    
    @Column(name = "SENT")
    private Date sent;

    @Column(name = "NOTE")
    private String note;
    
	/* Constructors */
	


}
