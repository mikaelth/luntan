package se.uu.ebc.luntan.repo;

import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import se.uu.ebc.luntan.entity.ExaminersDecision;
import se.uu.ebc.luntan.enums.EduBoard;

import java.util.Set;
import java.util.List;
import java.util.Date;

@Repository
@Transactional //(readOnly = true)
public interface ExaminersDecisionRepo extends JpaRepository<ExaminersDecision, Long>, JpaSpecificationExecutor<ExaminersDecision>{



}
