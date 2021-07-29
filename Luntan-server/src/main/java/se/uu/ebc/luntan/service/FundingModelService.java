package se.uu.ebc.luntan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import se.uu.ebc.luntan.entity.FundingModel;
import se.uu.ebc.luntan.repo.FundingModelRepo;
import se.uu.ebc.luntan.vo.FMVO;


import org.apache.log4j.Logger;

@Service
public class FundingModelService {

    private static Logger logger = Logger.getLogger(FundingModelService.class.getName());

	@Autowired
	FundingModelRepo fmRepo;



	/* Courses */
	
	public List<FMVO> getAllFMs() throws Exception {
		List<FMVO> fmVO = new ArrayList<FMVO>();
		try {	
			for (FundingModel fm : fmRepo.findAll()) {
 				fmVO.add(new FMVO(fm));
 			}
         	return fmVO;        	        
        } catch (Exception e) {

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

}