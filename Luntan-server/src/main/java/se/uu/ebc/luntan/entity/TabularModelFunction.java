package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ElementCollection;

import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.MapContext;

import org.apache.log4j.Logger;

@Entity
@Table(name = "TABULAR_MODEL_DATA")
@Inheritance(strategy = InheritanceType.JOINED)
public class TabularModelFunction  extends Auditable {
	
    private static Logger log = Logger.getLogger(TabularModelFunction.class.getName());
	
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

    
    @OneToMany(mappedBy = "tabledValues")
    private Set<FundingModel> fundingModels;
    
    @Column(name = "DESIGNATION", length = 255)
    private String designation;

    @Column(name = "NOTE", length = 255)
    private String note;

    /* Tabular model */

    @ElementCollection
    private Map<Integer,Float> valueTable;
    
 
   /* Setters and getters */
 
    public String getDesignation()
    {
    	return this.designation;
    }

    public void setDesignation(String designation)
    {
    	this.designation = designation;
    }


    public String getNote()
    {
    	return this.note;
    }

    public void setNote(String note)
    {
    	this.note = note;
    }


 	public void setValueTable(TreeMap<Integer,Float> valueTable) {
 		this.valueTable = valueTable;
 	}
 	public TreeMap<Integer,Float> getValueTable() {
		if (!(this.valueTable instanceof TreeMap)) {
			this.valueTable = new TreeMap<Integer,Float>(this.valueTable);
		}
 		return (TreeMap)this.valueTable;
 	}
	
	/* Constructors */
	
	public TabularModelFunction() {
	}
	
	/* Business methods */

	
	float getTabledValue(Integer students) {
		return valueTable.get(getValueTable().floorKey(students));
	}
	
	
	 
}
