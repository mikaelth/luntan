package se.uu.ebc.luntan.repo;

import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import se.uu.ebc.luntan.entity.Examiner;
import se.uu.ebc.luntan.enums.EduBoard;

import java.util.Set;
import java.util.List;

@Repository
@Transactional //(readOnly = true)
public interface ExaminerRepo extends JpaRepository<Examiner, Long>, JpaSpecificationExecutor<Examiner>{

	@Query("SELECT e FROM Examiner AS e WHERE (e.examinerList.listStatus = 1 AND e.course.board = ?1)")
	public Set<Examiner> findAvailableByBoard(EduBoard board);
	
}
