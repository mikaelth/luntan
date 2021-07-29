package se.uu.ebc.luntan.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import se.uu.ebc.luntan.entity.ExaminersList;
import se.uu.ebc.luntan.entity.ExaminersDecision;

import java.util.Date;

@Repository
@Transactional //(readOnly = true)
public interface ExaminersListRepo extends JpaRepository<ExaminersList, Long>, JpaSpecificationExecutor<ExaminersList>{


	@Query(value="select * from examiners_list as el where board=?1 AND decision_date < ?2 order by decision_date DESC limit 1", 
		nativeQuery=true)
	public ExaminersDecision findPreceeding(String board, Date date);


}
