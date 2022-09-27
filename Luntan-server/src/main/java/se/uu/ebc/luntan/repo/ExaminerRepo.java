package se.uu.ebc.luntan.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import se.uu.ebc.luntan.entity.Examiner;
import se.uu.ebc.luntan.entity.ExaminersList;
import se.uu.ebc.luntan.enums.EduBoard;

import java.util.Set;
import java.util.List;

@Repository
@Transactional //(readOnly = true)
public interface ExaminerRepo extends JpaRepository<Examiner, Long>, JpaSpecificationExecutor<Examiner>{

	@Query("SELECT e FROM Examiner AS e WHERE (e.examinerList.listStatus = 1 AND e.course.board = ?1) ORDER BY e.course.board, e.course.code, e.rank")
	public List<Examiner> findAvailableByBoard(EduBoard board);

	@Query("SELECT e FROM Examiner AS e WHERE (e.examinerList.listStatus = 1 AND e.course.board = ?1) ORDER BY e.examiner, e.course.code")
	public List<Examiner> findAvailableByBoardExOrder(EduBoard board);

	@Query("SELECT e FROM Examiner AS e WHERE e.examinerList = ?1 ORDER BY e.course.code, e.rank")
	public List<Examiner> findByDecision(ExaminersList decision);

	@Query("SELECT e FROM Examiner AS e WHERE e.examinerList = ?1 ORDER BY e.examiner, e.course.code")
	public List<Examiner> findByDecisionExOrder(ExaminersList decision);

	@Query("SELECT DISTINCT e.examiner FROM Examiner AS e")
	public Set<String> findDesignatedLDAPEntries();

	@Query("SELECT e FROM Examiner AS e WHERE e.examinerList.listStatus = 1 ORDER BY e.course.board, e.course.code, e.rank")
	public List<Examiner> findAvailable();

//	@Query(value="select distinct c.code,e.ldap_entry!=se.ldap_entry as different  from examiner as e join examiner as se on (e.`course_fk`=se.course_fk AND e.rank=se.rank) join course as c on e.course_fk=c.id where e.list_fk=?1 and se.list_fk=?2 UNION select distinct c.code,2 as different from examiner as e join course as c on e.course_fk=c.id where list_fk=?1 and course_fk not in (select distinct course_fk from examiner as se where list_fk=?2)", 
//		nativeQuery=true)
 
	@Query(value="select distinct c.code, group_concat(e.ldap_entry) != group_concat(se.ldap_entry) as different  from examiner as e join examiner as se on (e.`course_fk`=se.course_fk AND e.rank=se.rank) join course as c on e.course_fk=c.id where e.list_fk=?1 and se.list_fk=?2 group by e.course_fk, se.course_fk UNION select distinct c.code,2 as different from examiner as e join course as c on e.course_fk=c.id where list_fk=?1 and course_fk not in (select distinct course_fk from examiner as se where list_fk=?2)", 
		nativeQuery=true)	public List<Object[]> compareELists(Long newId, Long prevId);
 
		
 	@Query(value="select distinct c.code, group_concat(e.ldap_entry) != group_concat(se.ldap_entry) as different  from examiner as e join examiner as se on (e.`course_fk`=se.course_fk AND e.rank=se.rank) join course as c on e.course_fk=c.id where e.list_fk=?1 and se.list_fk=?2 and c.board=?3 group by e.course_fk, se.course_fk UNION select distinct c.code,2 as different from examiner as e join course as c on e.course_fk=c.id where list_fk=?1 and c.board=?3 and course_fk not in (select distinct course_fk from examiner as se where list_fk=?2)", 
		nativeQuery=true)	public List<Object[]> compareELists(Long newId, Long prevId, String boardAbbreviation);

	@Query("SELECT e FROM Examiner AS e WHERE e.examinerList = ?1 AND e.rank=1")
	public List<Examiner> findPrimariesByDecision(ExaminersList decision);
		
}
