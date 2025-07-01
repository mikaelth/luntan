package se.uu.ebc.luntan.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import se.uu.ebc.luntan.entity.IndividualCourseRegistration;
import se.uu.ebc.luntan.entity.IndividualCourseCreditBasis;

import java.util.List;


@Repository
@Transactional(readOnly = true)
public interface IndividualCourseRegRepo extends JpaRepository<IndividualCourseRegistration, Long>, JpaSpecificationExecutor<IndividualCourseRegistration>{

//    public Programme findById(Long id);

 	@Query("SELECT distinct cr FROM IndividualCourseRegistration AS cr WHERE cr.creditBasisRecord is null")
 	public List<IndividualCourseRegistration> findUnattached();

 	@Query("SELECT distinct cr FROM IndividualCourseRegistration AS cr WHERE (cr.creditBasisRecord is null AND cr.startDate < CURRENT_DATE)")
 	public List<IndividualCourseRegistration> findUnattachedBeforeToday();

//  	@Query("SELECT count(*) FROM IndividualCourseRegistration AS cr WHERE cr.creditBasisRecord = ?1")
//  	public Integer numberOfRegs(IndividualCourseCreditBasis iccBasis);

}
