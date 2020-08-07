package se.uu.ebc.luntan.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.NamingException;
import javax.naming.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;

// import org.springframework.ldap.core.AttributesMapper;
// import org.springframework.ldap.core.LdapTemplate;
// import org.springframework.ldap.filter.AndFilter;
// import org.springframework.ldap.filter.EqualsFilter;
// import org.springframework.ldap.query.LdapQuery;
// import org.springframework.ldap.query.SearchScope;
// import org.springframework.ldap.support.LdapUtils;
// import org.springframework.ldap.support.LdapNameBuilder;
// import static org.springframework.ldap.query.LdapQueryBuilder.query;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.extern.slf4j.Slf4j;

import se.uu.ebc.luntan.repo.CourseRepo;
import se.uu.ebc.luntan.repo.ExaminerRepo;
import se.uu.ebc.luntan.repo.ExaminersDecisionRepo;
import se.uu.ebc.luntan.repo.ExaminersListRepo;
import se.uu.ebc.luntan.repo.ExaminersWorkingListRepo;

import se.uu.ebc.luntan.vo.ExaminerVO;
import se.uu.ebc.luntan.vo.ExListVO;
import se.uu.ebc.luntan.entity.Examiner;
import se.uu.ebc.luntan.entity.ExaminersDecision;
import se.uu.ebc.luntan.entity.ExaminersList;
import se.uu.ebc.luntan.entity.ExaminersWorkingList;
import se.uu.ebc.luntan.enums.EduBoard;

import se.uu.ebc.ldap.Staff;

@Service
@Slf4j
public class ExaminersService {

    @Autowired
	CourseRepo courseRepo;

    @Autowired
	ExaminerRepo examinerRepo;

    @Autowired
	ExaminersDecisionRepo edRepo;
   
    @Autowired
	ExaminersWorkingListRepo exwlRepo;
 
    @Autowired
	ExaminersListRepo exlRepo;

 
 
 	/* Examiners */

	public List<ExaminerVO> getAllExaminers() throws Exception {
		List<ExaminerVO> eVO = new ArrayList<ExaminerVO>();
		try {	
			for (Examiner ex : examinerRepo.findAll()) {
 				eVO.add(new ExaminerVO(ex));
 			}
         	return eVO;        	        
        } catch (Exception e) {
			log.error("getAllExaminers got a pesky exception: "+ e + e.getCause());

			return null;
			
        }
    }
	
 
/* 
	public List<ExaminerVO> getAvailableNUN() throws Exception {
		List<ExaminerVO> eVO = new ArrayList<ExaminerVO>();
		try {	
			for (Examiner ex : examinerRepo.findAvailableByBoard(EduBoard.NUN)) {
 				eVO.add(new ExaminerVO(ex));
 			}
         	return eVO;        	        
        } catch (Exception e) {
			log.error("getAvailableNUN got a pesky exception: "+ e + e.getCause());

			return null;
			
        }
    }
	public List<ExaminerVO> getAvailableTUN() throws Exception {
		List<ExaminerVO> eVO = new ArrayList<ExaminerVO>();
		try {	
			for (Examiner ex : examinerRepo.findAvailableByBoard(EduBoard.TUN)) {
 				eVO.add(new ExaminerVO(ex));
 			}
         	return eVO;        	        
        } catch (Exception e) {
			log.error("getAvailableTUN got a pesky exception: "+ e + e.getCause());

			return null;
			
        }
    }
 */
 
 	public List<ExaminerVO> getAvailableByBoard(EduBoard board) throws Exception {
		List<ExaminerVO> eVO = new ArrayList<ExaminerVO>();
		try {	
			for (Examiner ex : examinerRepo.findAvailableByBoard(board)) {
 				eVO.add(new ExaminerVO(ex));
 			}
         	return eVO;        	        
        } catch (Exception e) {
			log.error("getAvailableByBoard got a pesky exception: "+ e + e.getCause());

			return null;
			
        }
    }


    public ExaminerVO saveExaminer(ExaminerVO eVO) throws Exception {
    	Examiner ex = eVO.getId() == null ? toExaminer(eVO) : toExaminer(examinerRepo.findById(eVO.getId()).get(), eVO);

		log.debug("saveExaminer, ex "+ReflectionToStringBuilder.toString(ex, ToStringStyle.MULTI_LINE_STYLE));

    	// only update if examiner is not tied to decision
    	if (!ex.decided()) {
    		examinerRepo.save(ex);
    	}
		return new ExaminerVO(ex);
    
    }

    public synchronized void deleteExaminer(Long cID) throws Exception {
		Examiner ex = examinerRepo.findById(cID).get();
    	// only update if examiner is not tied to decision
		if (!ex.decided())  {
			examinerRepo.delete(ex);
		}
    }

	private Examiner toExaminer (ExaminerVO eVO) throws Exception {
 		return toExaminer (new Examiner(), eVO);
   	}

	private Examiner toExaminer (Examiner ex, ExaminerVO eVO) throws Exception {


		try {
			if (!ex.decided()) {			
				ex.setCourse(courseRepo.findById(eVO.getCourseId()).get());
				if (eVO.isDecided()) {
					ex.setExaminerList(edRepo.findById(eVO.getDecisionId()).get());
				} else {
					ex.setExaminerList(this.getWorkingList());
				}
				ex.setExaminer(eVO.getLdapEntry()) ;
				ex.setRank(eVO.getRank()) ;
				ex.setNote(eVO.getNote()) ;
			}
		} catch (Exception e) {
			log.error("toExaminer got a pesky exception: "+ e + e.getCause());
		} finally {
			return ex;
		}
	}

	private synchronized ExaminersList getWorkingList() {
		
		ExaminersWorkingList theList;
//		Optional<List<ExaminersWorkingList>> oexwl = exwlRepo.findAll();
		List<ExaminersWorkingList> exwl = exwlRepo.findAll();
		
		if (exwl.size() > 0) {
//			List<ExaminersWorkingList> exwl = oexwl.get();
			theList = exwl.remove(0);
			if (exwl.size() > 0) {
				for (ExaminersWorkingList aList : exwl) {
					theList.getExaminers().addAll(aList.getExaminers());
					exwlRepo.delete(aList);
				}
			}
		} else {
			theList = new ExaminersWorkingList();
			exwlRepo.save(theList);
		} 
		log.debug("getWorkingList, theList "+ReflectionToStringBuilder.toString(theList, ToStringStyle.MULTI_LINE_STYLE));
		
		return theList;
	}


 	/* ExaminersList */

	public List<ExListVO> getAllExaminersLists() throws Exception {
		List<ExListVO> exlVO = new ArrayList<ExListVO>();
		try {	
			for (ExaminersList exl : exlRepo.findAll()) {
 				exlVO.add(new ExListVO(exl));
 			}
         	return exlVO;        	        
        } catch (Exception e) {
			log.error("getAllExaminersLists got a pesky exception: "+ e + e.getCause());

			return null;
			
        }
    }
	
     public ExListVO updateExaminersDecision (ExListVO exlVO) throws Exception {
		return new ExListVO(saveExaminersDecision(edRepo.findById(exlVO.getId()).get(), exlVO));
    
    }
   public ExaminersDecision saveExaminersDecision (ExaminersDecision ed, ExListVO exlVO) throws Exception {
    	ed.setDecisionDate(exlVO.getDecisionDate());
    	ed.setBoard(exlVO.getBoard());
    	ed.setNote(exlVO.getNote());
    	ed.setDefaultExaminers(exlVO.getDefaultExaminers());
   		edRepo.save(ed);
		return ed;
    
    }

    public synchronized void deleteExaminersDecision(Long edID) throws Exception {
		ExaminersDecision ed = edRepo.findById(edID).get();
		edRepo.delete(ed);
    }

    public synchronized ExListVO createExaminersDecision(ExListVO exlVO) throws Exception {
		try {
			ExaminersDecision exd = saveExaminersDecision(new ExaminersDecision(),exlVO);
			cloneExaminers(exd);
			return new ExListVO(exd);
		} catch (Exception e) {
			log.error("createExaminersDecision got a pesky exception: "+ e + e.getCause());
			return null;
		} 
    }
    
    public List<ExaminerVO> createDecisionDocument() {
    	return null;
    }

	private void cloneExaminers (ExaminersDecision exd) throws Exception {
		log.debug("cloning for " + exd.getBoard());
		for (Examiner ex : examinerRepo.findAvailableByBoard(exd.getBoard())) {
			log.debug("cloning for " + ex.getExaminer());
			cloneExaminer(exd,ex);
		}
	}

	private void cloneExaminer (ExaminersDecision exd, Examiner ex) {
		
		examinerRepo.save(ex.toBuilder().
						id(null).
						examinerList(exd).
						build()
					);
	}

}