package se.uu.ebc.luntan.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import se.uu.ebc.luntan.entity.CourseInstance;
import se.uu.ebc.luntan.entity.EconomyDocument;

import java.util.List;

@Repository
@Transactional(readOnly = false)
public interface CourseInstanceRepo extends JpaRepository<CourseInstance, Long>, JpaSpecificationExecutor<CourseInstance>{

//    public CourseInstance findById(Long id);  
 
	@Query("SELECT ci FROM CourseInstance AS ci WHERE ci.economyDoc = ?1")
	public List<CourseInstance> findByEconomyDoc(EconomyDocument ed);

	@Query("SELECT ci FROM CourseInstance AS ci WHERE (ci.balanceRequest=true AND ci.balancedEconomyDoc IS NULL)")
	public List<CourseInstance> findBalanceRequestedNotHandled();
 
	@Query("SELECT ci FROM CourseInstance AS ci WHERE (ci.economyDoc = ?1 AND (ci.economyDoc.locked = false OR ci.economyDoc.lockDate > ci.creationDate))")
	public List<CourseInstance> findRegularByEconomyDoc(EconomyDocument ed);

	@Query("SELECT ci FROM CourseInstance AS ci WHERE (ci.economyDoc = ?1 AND ci.economyDoc.locked = true AND ci.creationDate > ci.economyDoc.lockDate)")
	public List<CourseInstance> findSupplementaryByEconomyDoc(EconomyDocument ed);
	
//	@Query("SELECT ci FROM CourseInstance AS ci WHERE ci.economyDoc = ?1")
	public CourseInstance findByInstanceCodeAndEconomyDoc(String instanceCode, EconomyDocument edoc);

	@Query("SELECT ci FROM CourseInstance AS ci WHERE (ci.course.code = ?1 AND ci.economyDoc = ?2)")
	public List<CourseInstance> findByCourseCodeAndEconomyDoc(String courseCode, EconomyDocument edoc);

}
