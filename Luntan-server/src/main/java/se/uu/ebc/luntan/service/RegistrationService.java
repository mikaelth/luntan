package se.uu.ebc.luntan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.Calendar;

import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.uu.ebc.luntan.enums.Department;
import se.uu.ebc.luntan.enums.IndCourseTeacherKind;

import se.uu.ebc.luntan.entity.IndividualYearlyCourse;
import se.uu.ebc.luntan.entity.IndividualCourseRegistration;
import se.uu.ebc.luntan.entity.IndividualCourseTeacher;
import se.uu.ebc.luntan.entity.IndividualCourseCreditBasis;

import se.uu.ebc.luntan.vo.IndRegVO;
import se.uu.ebc.luntan.vo.IndCourseTeacherVO;
import se.uu.ebc.luntan.vo.IndCCBasisVO;

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

	private final Department DEFAULT_DEPARTMENT = Department.IBG;

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
			c.setIbgReg(cvo.isIbgReg());
			c.setNote(cvo.getNote()) ;

/*
			if (cvo.getCoordinatorId()!= null){
				c.setCoordinator(teacherRepo.findById(cvo.getCoordinatorId()).get());
			}
 */

			c.setDepartment( extractCoordinatingDepartment(c.getTeachers()) ) ;

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

	private Department extractCoordinatingDepartment(Set<IndividualCourseTeacher> teachers) {
		List<IndividualCourseTeacher> coordinators = teachers
			.stream()
  			.filter( c -> c.getTeacherType().equals (IndCourseTeacherKind.Coordinator) )
			.collect(toList());

		Department dept = (coordinators.size() == 0) ? DEFAULT_DEPARTMENT : extractDepartment(coordinators.get(0).getFullDepartment());

		return dept;
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
		log.debug("deleteICTeacher: "+ cID);

		ictRepo.delete(c);
    }

	private IndividualCourseTeacher toICTeacher (IndCourseTeacherVO cvo) throws Exception {
 		return toICTeacher (new IndividualCourseTeacher(), cvo);
   	}

	private IndividualCourseTeacher toICTeacher (IndividualCourseTeacher c, IndCourseTeacherVO cvo) throws Exception {


		try {
			c.setId(cvo.getId()) ;

			c.setLdapEntry(cvo.getLdapEntry());
			c.setTeacherType(cvo.getTeacherType()) ;
			c.setNote(cvo.getNote()) ;

			c.setExternal(cvo.isExternal());
			c.setName(cvo.getName());
			c.setPhone(cvo.getPhone());
			c.setFullDepartment(cvo.getFullDepartment());
			c.setEmail(cvo.getEmail());

			c.setDepartment(extractDepartment(cvo.getFullDepartment()));

			if (cvo.getAssignmentId()!= null){
				IndividualCourseRegistration reg = regsRepo.findById(cvo.getAssignmentId()).get();
				c.setAssignment(reg);
				if (cvo.getTeacherType() == IndCourseTeacherKind.Coordinator) {
					reg.setDepartment(c.getDepartment());
				}
			}
		} catch (Exception e) {
			log.error("toICTeacher got a pesky exception: "+ e + e.getCause());
		} finally {
			return c;
		}
	}

	private Department extractDepartment(String fullDepartment) {
		Department dp = DEFAULT_DEPARTMENT;
		try {
		   for(Department dept : Department.values()) {
			   Pattern pattern = Pattern.compile(dept.deptName());
			   Matcher matcher = pattern.matcher(fullDepartment);
			   if (matcher.find()) {
				   dp = dept;
			   }
		   }
		} catch (Exception e) {

		} finally {
			return dp;
		}
	}

	/* Individual Course Credit Basis */

	public List<IndCCBasisVO> getAllICCBasis() throws Exception {
		List<IndCCBasisVO> cVO = new ArrayList<IndCCBasisVO>();
		try {
			for (IndividualCourseCreditBasis c : credRepo.findAll()) {
 				cVO.add(new IndCCBasisVO(c));
 			}
         	return cVO;
        } catch (Exception e) {
			log.error("getAllICCBasis got a pesky exception: "+ e + e.getCause());

			return null;

        }
    }

    public IndCCBasisVO saveICCBasis(IndCCBasisVO cVO) throws Exception {
    	IndividualCourseCreditBasis c = cVO.getId() == null ? toICCBasis(cVO) : toICCBasis(credRepo.findById(cVO.getId()).get(), cVO);
    	credRepo.save(c);
		if (c.getRegistrations().size()==0) {
			for (IndividualCourseRegistration cr : regsRepo.findUnattached()) {
				cr.setCreditBasisRecord(c);
				regsRepo.save(cr);
				regsRepo.save(cr);
			}
		}
		return new IndCCBasisVO(c);

    }

    public synchronized void deleteICCBasis(Long cID) throws Exception {
		IndividualCourseCreditBasis c = credRepo.findById(cID).get();
		for (IndividualCourseRegistration cr : c.getRegistrations()) {
			cr.setCreditBasisRecord(null);
		}
		credRepo.delete(c);
    }

	private IndividualCourseCreditBasis toICCBasis (IndCCBasisVO cvo) throws Exception {
 		return toICCBasis (new IndividualCourseCreditBasis(), cvo);
   	}

	private IndividualCourseCreditBasis toICCBasis (IndividualCourseCreditBasis c, IndCCBasisVO cvo) throws Exception {


		try {
			c.setId(cvo.getId()) ;
			c.setSent(cvo.getSent());
			c.setNote(cvo.getNote()) ;

		} catch (Exception e) {
			log.error("toICCBasis got a pesky exception: "+ e + e.getCause());
		} finally {
			return c;
		}
	}

}
