package se.uu.ebc.luntan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import se.uu.ebc.luntan.entity.CourseInstance;
import se.uu.ebc.luntan.entity.Course;
import se.uu.ebc.luntan.vo.CourseInstanceVO;
import se.uu.ebc.luntan.vo.CourseVO;
import se.uu.ebc.luntan.repo.CourseInstanceRepo;
import se.uu.ebc.luntan.repo.CourseRepo;
import se.uu.ebc.luntan.repo.EconomyDocumentRepo;
import se.uu.ebc.luntan.repo.FundingModelRepo;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;

@Service
public class CourseService {

    private static Logger logger = Logger.getLogger(CourseService.class.getName());

	@Autowired
	CourseRepo courseRepo;

	@Autowired
	CourseInstanceRepo ciRepo;

	@Autowired
	EconomyDocumentRepo edRepo;

	@Autowired
	FundingModelRepo fmRepo;

	/* Courses */

	public List<CourseVO> getAllCourses() throws Exception {
		List<CourseVO> cVO = new ArrayList<CourseVO>();
		try {	
			for (Course c : courseRepo.findAll()) {
 				cVO.add(new CourseVO(c));
 			}
         	return cVO;        	        
        } catch (Exception e) {
			logger.error("getAllCourses got a pesky exception: "+ e + e.getCause());

			return null;
			
        }
    }
    
/*	
    public CourseVO saveCourse(CourseVO cVO) throws Exception {
    	Course c = cVO.getId() == null ? toCourse(cVO) : toCourse(courseRepo.findById(cVO.getId()), cVO);
    	courseRepo.save(c);
		return new CourseVO(c);
    
    }


    public synchronized void deleteCourse(Long cID) throws Exception {
		Course c = courseRepo.findById(cID);
		courseRepo.delete(c);
    }

 
	private Course toCourse (CourseVO cvo) throws Exception {
 		return toCourse (new Course(), cvo);
   	}

	private Course toCourse (Course c, CourseVO cvo) throws Exception {


		try {
			c.setId(cvo.getId()) ;
			c.setCode(cvo.getCode()) ;
			c.setSeName(cvo.getSeName()) ;
			c.setEnName(cvo.getEnName()) ;
			c.setCourseGroup(cvo.getCourseGroup()) ;
			c.setPeriod(cvo.getPeriod()) ;
			c.setNote(cvo.getNote()) ;
			c.setCredits(cvo.getCredits()) ;


		} catch (Exception e) {
			logger.error("toCourse got a pesky exception: "+ e + e.getCause());
		} finally {
			return c;
		}
	}
 

*/

	/* CourseInstances */
	
	public List<CourseInstanceVO> getAllCourseInstances() throws Exception {
		List<CourseInstanceVO> cVO = new ArrayList<CourseInstanceVO>();
		try {	
			for (CourseInstance ci : ciRepo.findAll()) {
				logger.debug("getAllCourseInstances, ci "+ReflectionToStringBuilder.toString(ci, ToStringStyle.MULTI_LINE_STYLE));
 				cVO.add(new CourseInstanceVO(ci));
 			}
         	return cVO;        	        
        } catch (Exception e) {
			logger.error("getAllCourseInstances got a pesky exception: "+ e + e.getCause());

			return null;
			
        }
    }
    

    public CourseInstanceVO saveCourseInstance(CourseInstanceVO cVO) throws Exception {
    	CourseInstance ci = cVO.getId() == null ? toCourseInstance(cVO) : toCourseInstance(ciRepo.findById(cVO.getId()), cVO);
    	ciRepo.save(ci);
		return new CourseInstanceVO(ci);
    
    }


    public synchronized void deleteCourseInstance(Long cID) throws Exception {
		CourseInstance ci = ciRepo.findById(cID);
		ciRepo.delete(ci);
    }


	private CourseInstance toCourseInstance (CourseInstanceVO cVO) throws Exception {
 		return toCourseInstance (new CourseInstance(), cVO);
   	}

	private CourseInstance toCourseInstance (CourseInstance ci, CourseInstanceVO cVO) throws Exception {


		try {
			ci.setId(cVO.getId());
			ci.setExtraDesignation(cVO.getExtraDesignation());
			ci.setRegisteredStudents(cVO.getRegisteredStudents());
			ci.setStartRegStudents(cVO.getStartRegStudents());
    		ci.setBalanceRequest(cVO.isBalanceRequest());
    		ci.setNote(cVO.getNote());
    		
    		ci.setGrantDistribution(cVO.getGrantDistribution());

			ci.setEconomyDoc(edRepo.findById(cVO.getEconomyDocId()));
			ci.setCourse(courseRepo.findById(cVO.getCourseId()));
			ci.setFundingModel(fmRepo.findById(cVO.getFundingModelId()));

			if (cVO.getPreceedingCIId()!= null){
				ci.setPreceedingCI(ciRepo.findById(cVO.getPreceedingCIId()));
			}
			if (cVO.getBalancedEconomyDocId()!= null){
				ci.setBalancedEconomyDoc(edRepo.findById(cVO.getBalancedEconomyDocId()));
			}
			




		} catch (Exception e) {
			logger.error("toCourseInstance got a pesky exception: "+ e + e.getCause());
		} finally {
			return ci;
		}
	}



    
}