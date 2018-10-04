package se.uu.ebc.luntan.repo;

import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import se.uu.ebc.luntan.entity.FundingModel;

import java.util.Set;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface FundingModelRepo extends JpaRepository<FundingModel, Long>, JpaSpecificationExecutor<FundingModel>{

    public FundingModel findById(Long id);  
     
}
