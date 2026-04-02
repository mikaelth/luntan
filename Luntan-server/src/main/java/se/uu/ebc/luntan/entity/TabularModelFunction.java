package  se.uu.ebc.luntan.entity;

import java.util.Set;
import java.util.Map;
import java.util.TreeMap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ElementCollection;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "TABULAR_MODEL_DATA")
@Inheritance(strategy = InheritanceType.JOINED)
public class TabularModelFunction  extends Auditable {

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
