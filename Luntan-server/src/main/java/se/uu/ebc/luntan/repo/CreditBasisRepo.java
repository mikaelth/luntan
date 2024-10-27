package se.uu.ebc.luntan.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import se.uu.ebc.luntan.entity.CreditBasis;

import java.util.List;


@Repository
@Transactional(readOnly = true)
public interface CreditBasisRepo extends JpaRepository<CreditBasis, Long>, JpaSpecificationExecutor<CreditBasis>{

//    public Programme findById(Long id);

// 	@Query("SELECT distinct p FROM Programme AS p WHERE p.inactive=false")
// 	public List<Programme> findActive();

}
