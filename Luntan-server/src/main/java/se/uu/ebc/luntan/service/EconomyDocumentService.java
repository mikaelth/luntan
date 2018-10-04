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

import se.uu.ebc.luntan.entity.EconomyDocument;
import se.uu.ebc.luntan.repo.EconomyDocumentRepo;
import se.uu.ebc.luntan.vo.EconomyDocVO;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;

@Service
public class EconomyDocumentService {

    private static Logger logger = Logger.getLogger(EconomyDocumentService.class.getName());

	@Autowired
	EconomyDocumentRepo edRepo;



	/* EconomyDocument */
	
	public List<EconomyDocVO> getAllEDs() throws Exception {
		List<EconomyDocVO> edVO = new ArrayList<EconomyDocVO>();
		try {	
			for (EconomyDocument ed : edRepo.findAll()) {
 				edVO.add(new EconomyDocVO(ed));
 			}
         	return edVO;        	        
        } catch (Exception e) {

			logger.error("getAllEDs got a pesky exception: "+ e + e.getCause());

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