package se.uu.ebc.luntan.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import se.uu.ebc.luntan.entity.EconomyDocument;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface EconomyDocumentRepo extends JpaRepository<EconomyDocument, Long>, JpaSpecificationExecutor<EconomyDocument>{

//    public EconomyDocument findById(Long id);  
    public EconomyDocument findByYear(Integer year);  

	@Query("SELECT distinct ed.year as yr FROM EconomyDocument AS ed order by yr DESC")
	public List<Integer> getYears();

	@Query("SELECT ed FROM EconomyDocument AS ed where (ed.year >= ?1 AND ed.locked = false) order by ed.year ASC")
	public List<EconomyDocument> availBalanceDoc (Integer year);
 
}
