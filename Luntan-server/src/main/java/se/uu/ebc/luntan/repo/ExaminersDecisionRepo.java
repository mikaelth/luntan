package se.uu.ebc.luntan.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import se.uu.ebc.luntan.enums.EduBoard;
import se.uu.ebc.luntan.entity.ExaminersDecision;


@Repository
@Transactional //(readOnly = true)
public interface ExaminersDecisionRepo extends JpaRepository<ExaminersDecision, Long>, JpaSpecificationExecutor<ExaminersDecision>{



}
