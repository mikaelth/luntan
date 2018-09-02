package  se.uu.ebc.luntan.entity;

import java.util.Set;
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

import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.MapContext;

import org.apache.log4j.Logger;

@Entity
@Table(name = "EXPRESSION_MODEL")
public class ExpressionFundingModel  extends FundingModel {

    private static Logger log = Logger.getLogger(ExpressionFundingModel.class.getName());
	private static final JexlEngine jexl = new JexlBuilder().cache(512).strict(true).silent(false).create();
    
/* 
    @OneToMany(mappedBy = "course")
    private Set<CourseInstance> courseInstances;
 */
    
    @Column(name = "EXPRESSION", length = 255)
    private String expression;

    
 
 
     


	/* Setters and getters */
 
     public String getExpression()
    {
    	return this.expression;
    }

    public void setExpression(String expression)
    {
    	this.expression = expression;
    }


    

	/* Business methods */
	@Override
	public Float computeFunding(Integer registerdStudents, Float ects, Integer baseLevel) {

		// Create an expression object for our calculation
		JexlExpression e = jexl.createExpression( this.expression );

		// populate the context
		JexlContext context = new MapContext();
		context.set("studentNumber", registerdStudents);
		context.set("ects", ects);
		context.set("baseLevel", baseLevel);

		// work it out
		Number result = (Number) e.evaluate(context);

		return result.floatValue()/REFERENCE_BASE_LEVEL;
	}   
    
}
