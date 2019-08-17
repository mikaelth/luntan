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
import se.uu.ebc.luntan.entity.EconomyDocGrant;
import se.uu.ebc.luntan.entity.CourseInstance;
import se.uu.ebc.luntan.repo.EconomyDocumentRepo;
import se.uu.ebc.luntan.repo.EDGRepo;
//import se.uu.ebc.luntan.repo.CourseInstanceRepo;
import se.uu.ebc.luntan.vo.EconomyDocVO;
import se.uu.ebc.luntan.vo.EDGVO;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;

@Service
public class EconomyDocumentService {

    private static Logger logger = Logger.getLogger(EconomyDocumentService.class.getName());

	@Autowired
	EconomyDocumentRepo edRepo;

	@Autowired
	EDGRepo edgRepo;

	@Autowired
	CourseService courseService;

//	@Autowired
//	CourseInstanceRepo ciRepo;


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
    

    public EconomyDocVO saveEDoc(EconomyDocVO edVO) throws Exception {
    	EconomyDocument ed = edVO.getId() == null ? toEDoc(edVO) : toEDoc(edRepo.findById(edVO.getId()).get(), edVO);
    	edRepo.save(ed);

		/* If creating new EconomyDocument and flag is set in VO, clone all courses from previous year to new EconomyDocument */
    	if (edVO.getId() == null && edVO.isCloneCourses()) {
    		courseService.cloneCourseInstances(ed,edRepo.findByYear(ed.getYear()-1));
    	}

		return new EconomyDocVO(ed);
    
    }


    public synchronized void deleteEDoc(Long eID) throws Exception {
		EconomyDocument ed = edRepo.findById(eID).get();
		edRepo.delete(ed);
    }

 
	private EconomyDocument toEDoc (EconomyDocVO edVO) throws Exception {
 		return toEDoc (new EconomyDocument(), edVO);
   	}

	private EconomyDocument toEDoc (EconomyDocument ed, EconomyDocVO edVO) throws Exception {


		try {
			
			boolean lckChg = ed.isLocked() ^ edVO.isLocked();

			ed.setId(edVO.getId()) ;
			ed.setBaseValue(edVO.getBaseValue());
			ed.setYear(edVO.getYear());
			ed.setNote(edVO.getNote());
			ed.setAccountedDepts(edVO.getAccountedDepts());
			ed.setLocked(edVO.isLocked());
//			ed.setCourseInstances(edVO.getCourseInstances());

			/* If lock status has changed, we need to update and save the course instances as well */
			if( lckChg ) {
				for (CourseInstance ci : ed.getCourseInstances()) {
					ci.updateLock();
				}
			}

		} catch (Exception e) {
			logger.error("toEDoc got a pesky exception: "+ e + e.getCause());
		} finally {
			return ed;
		}
	}
 


	/* EconomyDocumentGrant */
	
	public List<EDGVO> getAllEDGrants() throws Exception {
		List<EDGVO> edgVO = new ArrayList<EDGVO>();
		try {	
			for (EconomyDocGrant edg : edgRepo.findAll()) {
 				edgVO.add(new EDGVO(edg));
 			}
         	return edgVO;        	        
        } catch (Exception e) {

			logger.error("getAllEDs got a pesky exception: "+ e + e.getCause());

			return null;
			
        }
    }
    

    public EDGVO saveEDGrant(EDGVO edgVO) throws Exception {
    	EconomyDocGrant edg = edgVO.getId() == null ? toEDGrant(edgVO) : toEDGrant(edgRepo.findById(edgVO.getId()).get(), edgVO);
    	edgRepo.save(edg);

		return new EDGVO(edg);
    
    }


    public synchronized void deleteEDGrant(Long eID) throws Exception {
		EconomyDocGrant edg = edgRepo.findById(eID).get();
		edgRepo.delete(edg);
    }

 
	private EconomyDocGrant toEDGrant (EDGVO edgVO) throws Exception {
 		return toEDGrant (new EconomyDocGrant(), edgVO);
   	}

	private EconomyDocGrant toEDGrant (EconomyDocGrant edg, EDGVO edgVO) throws Exception {


		try {
			
			edg.setId(edgVO.getId());
			
			edg.setEconomyDoc(edRepo.findById(edgVO.getEconomyDocId()).get());
			
			edg.setNote(edgVO.getNote());
			edg.setItemDesignation(edgVO.getItemDesignation());
			edg.setGrantKind(edgVO.getGrantKind());		
			edg.setTotalGrant(edgVO.getTotalGrant());

			edg.setGrantDistribution(edgVO.getGrantDistribution());

		} catch (Exception e) {
			logger.error("toEDGrant got a pesky exception: "+ e + e.getCause());
		} finally {
			return edg;
		}
	}
	

}