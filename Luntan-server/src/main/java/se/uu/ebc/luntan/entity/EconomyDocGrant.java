package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Embedded;
import javax.persistence.ElementCollection;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import lombok.extern.slf4j.Slf4j;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;

import se.uu.ebc.luntan.enums.*;
import se.uu.ebc.luntan.aux.GrantMaps;

@Slf4j
@Data
@Entity
@Table(name = "ECONOMYDOCGRANT")
public class EconomyDocGrant  extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @ManyToOne
    @NotNull
    @JoinColumn(name = "ECONOMY_DOC_FK")
	private EconomyDocument economyDoc;

    @Column(name = "DESIGNATION", length = 255)
	private String itemDesignation;

    @Column(name = "GRANTKIND", length = 255)
	@Enumerated(EnumType.STRING)    
    @NotNull
    private EDGKind grantKind;
    
    @Column(name="TOTALGRANT")
    private Float totalGrant;

	@ElementCollection
	@MapKeyEnumerated(EnumType.STRING)    
    private Map<Department,Float> grantDistribution = new HashMap<Department,Float>() ;

    @Column(name = "NOTE", length = 255)
    private String note;
 

	/* Business methods */
	
	public Map<Department,Float> getDistributedGrant() {
		Map<Department,Float> dist = new HashMap<Department,Float>();
		
		if (this.grantKind.isExplicit()) {
			dist = this.grantDistribution;
		} else {
			Map<Department,Float> sumDist = this.economyDoc.totalSum();
			Float grandTotal = sumDist.entrySet().stream()
				.filter(e -> !e.getKey().isImplicit())
				.map(Map.Entry::getValue)
				.reduce(0.0f, Float::sum);
			for (Department dep : economyDoc.getAccountedDepts()) {
				if (!dep.isImplicit()) {
					dist.put(dep,sumDist.get(dep)*this.totalGrant/grandTotal);
				} 
			}
		}
		
		return dist;
	}

}