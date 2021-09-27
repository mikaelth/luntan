package se.uu.ebc.luntan.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import se.uu.ebc.luntan.entity.FundingModel;
import se.uu.ebc.luntan.entity.CourseInstance;
import se.uu.ebc.luntan.entity.EconomyDocument;

import java.util.Set;

@Repository
@Transactional(readOnly = true)
public interface FundingModelRepo extends JpaRepository<FundingModel, Long>, JpaSpecificationExecutor<FundingModel>{

//    public FundingModel findById(Long id);  

	@Query("SELECT distinct ci.fundingModel FROM CourseInstance AS ci WHERE ci.economyDoc = ?1")
	public Set<FundingModel> findDistinctByEconDoc(EconomyDocument edoc);     

	@Query("SELECT distinct ci.fundingModel FROM CourseInstance AS ci WHERE ci.economyDoc in ?1")
	public Set<FundingModel> findDistinctByEDocs(Set<EconomyDocument> edocs);     

}
