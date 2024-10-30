package se.uu.ebc.luntan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Calendar;

import se.uu.ebc.luntan.entity.IndividualYearlyCourse;
import se.uu.ebc.luntan.entity.IndividualCourseRegistration;
import se.uu.ebc.luntan.entity.IndividualCourseTeacher;
import se.uu.ebc.luntan.vo.IndRegVO;
import se.uu.ebc.luntan.vo.IndCourseTeacherVO;
import se.uu.ebc.luntan.repo.IndividualCourseRegRepo;
import se.uu.ebc.luntan.repo.IndividualCourseTeacherRepo;
import se.uu.ebc.luntan.repo.CourseInstanceRepo;
import se.uu.ebc.luntan.repo.IndividualCourseCreditBasisRepo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RegistrationService {


	@Autowired
	IndividualCourseRegRepo regsRepo;

	@Autowired
	IndividualCourseTeacherRepo ictRepo;

	@Autowired
	CourseInstanceRepo ciRepo;

	@Autowired
	IndividualCourseCreditBasisRepo credRepo;

	/* Individual Course Registrations */

	public List<IndRegVO> getAllRegistrations() throws Exception {
		List<IndRegVO> cVO = new ArrayList<IndRegVO>();
		try {
			for (IndividualCourseRegistration c : regsRepo.findAll()) {
 				cVO.add(new IndRegVO(c));
 			}
         	return cVO;
        } catch (Exception e) {
			log.error("getAllRegistrations got a pesky exception: "+ e + e.getCause());

			return null;

        }
    }

    public IndRegVO saveRegistration(IndRegVO cVO) throws Exception {
    	IndividualCourseRegistration c = cVO.getId() == null ? toRegistration(cVO) : toRegistration(regsRepo.findById(cVO.getId()).get(), cVO);
    	regsRepo.save(c);
		return new IndRegVO(c);

    }

    public synchronized void deleteRegistration(Long cID) throws Exception {
		IndividualCourseRegistration c = regsRepo.findById(cID).get();
		regsRepo.delete(c);
    }

	private IndividualCourseRegistration toRegistration (IndRegVO cvo) throws Exception {
 		return toRegistration (new IndividualCourseRegistration(), cvo);
   	}

	private IndividualCourseRegistration toRegistration (IndividualCourseRegistration c, IndRegVO cvo) throws Exception {


		try {
			c.setId(cvo.getId()) ;

			c.setCourseEvalSetUp(cvo.isCourseEvalSetUp());
			c.setStudentDone(cvo.isStudentDone()) ;

			c.setStartDate(cvo.getStartDate()) ;
			c.setStudentName(cvo.getStudentName()) ;
			c.setRegDepartment(cvo.getRegDepartment()) ;
			c.setNote(cvo.getNote()) ;

/*
			if (cvo.getCoordinatorId()!= null){
				c.setCoordinator(teacherRepo.findById(cvo.getCoordinatorId()).get());
			}
 */

			if (cvo.getCourseInstanceId()!= null){
				c.setCourseBag((IndividualYearlyCourse)ciRepo.findById(cvo.getCourseInstanceId()).get());
			}

			if (cvo.getCreditBasisRecId()!= null){
				c.setCreditBasisRecord(credRepo.findById(cvo.getCreditBasisRecId()).get());
			}

		} catch (Exception e) {
			log.error("toRegistration got a pesky exception: "+ e + e.getCause());
		} finally {
			return c;
		}
	}



	/* Individual Course Teachers */

	public List<IndCourseTeacherVO> getAllICTeachers() throws Exception {
		List<IndCourseTeacherVO> cVO = new ArrayList<IndCourseTeacherVO>();
		try {
			for (IndividualCourseTeacher c : ictRepo.findAll()) {
 				cVO.add(new IndCourseTeacherVO(c));
 			}
         	return cVO;
        } catch (Exception e) {
			log.error("getAllICTeachers got a pesky exception: "+ e + e.getCause());

			return null;

        }
    }

    public IndCourseTeacherVO saveICTeacher(IndCourseTeacherVO cVO) throws Exception {
    	IndividualCourseTeacher c = cVO.getId() == null ? toICTeacher(cVO) : toICTeacher(ictRepo.findById(cVO.getId()).get(), cVO);
    	ictRepo.save(c);
		return new IndCourseTeacherVO(c);

    }

    public synchronized void deleteICTeacher(Long cID) throws Exception {
		IndividualCourseTeacher c = ictRepo.findById(cID).get();
		ictRepo.delete(c);
    }

	private IndividualCourseTeacher toICTeacher (IndCourseTeacherVO cvo) throws Exception {
 		return toICTeacher (new IndividualCourseTeacher(), cvo);
   	}

	private IndividualCourseTeacher toICTeacher (IndividualCourseTeacher c, IndCourseTeacherVO cvo) throws Exception {


		try {
			c.setId(cvo.getId()) ;

			c.setLdapEntry(cvo.getLdapEntry());
			c.setDepartment(cvo.getDepartment()) ;
			c.setTeacherType(cvo.getTeacherType()) ;
			c.setNote(cvo.getNote()) ;

			if (cvo.getAssignmentId()!= null){
				c.setAssignment(regsRepo.findById(cvo.getAssignmentId()).get());
			}


		} catch (Exception e) {
			log.error("toICTeacher got a pesky exception: "+ e + e.getCause());
		} finally {
			return c;
		}
	}

}
