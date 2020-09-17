package se.uu.ebc.luntan.repo;

import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

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

	
}
