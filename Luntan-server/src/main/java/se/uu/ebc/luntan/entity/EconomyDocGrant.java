package  se.uu.ebc.luntan.entity;

import java.util.Map;
import java.util.HashMap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;


import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import se.uu.ebc.luntan.enums.*;

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

    @Column(name="USEDFORKEY")
    private boolean usedForKey;

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