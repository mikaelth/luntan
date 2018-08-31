package se.uu.ebc.luntan.entity;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

import se.uu.ebc.luntan.entity.*;
import se.uu.ebc.luntan.enums.*;
import se.uu.ebc.luntan.repo.*;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;

//@ContextConfiguration(classes = {PersistenceContext.class})
@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
@ComponentScan("se.uu.ebc.luntan")
@SpringBootTest
public class EntityTest {

    private Logger log = Logger.getLogger(EntityTest.class.getName());

	@Autowired 
	FundingModelRepo fmRepo;
	@Autowired 
	ExpressionFundingModelRepo efmRepo;

	@Autowired
	private TestEntityManager entityManager;
 
   

    @Test
   	public void test() {
	
	System.out.println("In test");
	
	try { 
	
	ExpressionFundingModel efm = new ExpressionFundingModel();
	efm.setExpression("65000/15 * studentNumber * ects");
	efm.setDesignation("EnSlaskTest");
	
	log.info("Model " + efm.getDesignation() +", " +efm.computeFunding(20, 15.0f, 100000)); 
	
	testCourse();

	log.info("fmRepo "+ efmRepo);
	log.info("fmRepo content "+ efmRepo.findAll());
    entityManager.persist(efm);
    entityManager.flush();
//	efmRepo.save(efm);
	log.info("fmRepo content "+ fmRepo.findAll());
	
	for (FundingModel fm : fmRepo.findAll()) {
		log.info("Model " + fm.getDesignation()/* +", " +fm.computeFunding(20, 15.0f, 100000)*/); 
	}
			
	} catch (Exception e) {
		log.error("Pesky exception in test(), " + e + e.getCause());

	}

 	}

	private void testCourse() {

		try {		
			Course course = new Course ("1BG217", "Marinbiologi", 15.0f, "En test");

			Set<Department> acntSet = new HashSet<Department>();
			acntSet.add(Department.IBG);
			acntSet.add(Department.ICM);
			acntSet.add(Department.IEG);
			acntSet.add(Department.IOB);
			EconomyDocument econDoc = new EconomyDocument(2018,10000,false,"En test",acntSet);

			assertThat(econDoc.getYear()).isEqualTo(2018);	

			ExpressionFundingModel efm = new ExpressionFundingModel();
			efm.setExpression("65000/15 * studentNumber * ects");
			efm.setDesignation("EnSlasktest");

			Map<Department,Float>  gDist = new HashMap<Department,Float>();
			gDist.put(Department.IOB,0.6f);
			gDist.put(Department.IEG,0.3f);

			CourseInstance ci = new CourseInstance();
			ci.setFundingModel(efm);
			ci.setStartRegStudents(10);
			ci.setCourse(course);
			ci.setEconomyDoc(econDoc);
			ci.setGrantDistribution(gDist);
			ci.setExtraDesignation(CIDesignation.P4);
		
			System.out.println (ci.computeGrants());
		} catch (Exception e) {
			log.error("pesky exception in testCours(), " + e + e.getCause());
		}	
	}

}
