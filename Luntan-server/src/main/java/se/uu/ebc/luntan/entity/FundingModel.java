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
@Table(name = "FUNDING_MODEL")
@Inheritance(strategy = InheritanceType.JOINED)
public class FundingModel  extends Auditable {

	static final Integer REFERENCE_BASE_LEVEL = 1000;
	static final String DEFAULT_EXPRESSION = "baseLevel*ects*studentNumber";
	
    private static Logger log = Logger.getLogger(FundingModel.class.getName());
	private static final JexlEngine jexl = new JexlBuilder().cache(512).strict(true).silent(false).create();
	
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

    
    @OneToMany(mappedBy = "fundingModel")
    private Set<CourseInstance> courseInstances;
    
    @Column(name = "DESIGNATION", length = 255)
    private String designation;

    @Column(name = "NOTE", length = 255)
    private String note;

    /* Tabular model */

    @ElementCollection
    private Map<Integer,Float> valueTable;
    
	/* Expression model */

    @Column(name = "EXPRESSION", length = 255)
    private String expression;
	 
 
   /* Setters and getters */
 
    public String getDesignation()
    {
    	return this.designation;
    }

    public void setDesignation(String designation)
    {
    	this.designation = designation;
    }

     public String getExpression()
    {
    	return this.expression;
    }

    public void setExpression(String expression)
    {
    	this.expression = expression;
    }


    public String getNote()
    {
    	return this.note;
    }

    public void setNote(String note)
    {
    	this.note = note;
    }


/* 
	public void setValueTable (Map<Integer, Float> valueTable) {
		this.valueTable = valueTable;
	}
	
	public Map<Integer, Float> getValueTable() {
		return this.valueTable;
	}
 */

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
	
	public FundingModel() {
		this.expression = DEFAULT_EXPRESSION;
	}
	
	/* Business methods */

	
	public Integer getNumCourseInstances() {
		return courseInstances == null ? 0 : courseInstances.size();	
	}
	
	
//	public abstract Float computeFunding(Integer registerdStudents, Float ects, Integer baseLevel);   
	
	public Float computeFunding(Integer registerdStudents, Float ects, Integer baseLevel) {

		// Create an expression object for our calculation
		JexlExpression e = jexl.createExpression( this.expression );

		
		// populate the context
		JexlContext context = new MapContext();
		context.set("studentNumber", registerdStudents);
		context.set("ects", ects);
		context.set("baseLevel", baseLevel);

		// check for look-up values
		if (valueTable == null || valueTable.size() == 0) {
			context.set("tabled", 1);
		} else {
//			log.debug("computeFunding table reads " + valueTable.get(registerdStudents));
			log.debug("computeFunding table reads " + valueTable.get(getValueTable().floorKey(registerdStudents)));
//			context.set("tabled", valueTable.get(registerdStudents));
			context.set("tabled", valueTable.get(getValueTable().floorKey(registerdStudents)));
		}
		
		// work it out
		Number result = (Number) e.evaluate(context);

		return result.floatValue()/REFERENCE_BASE_LEVEL;
	}   

	 
}
