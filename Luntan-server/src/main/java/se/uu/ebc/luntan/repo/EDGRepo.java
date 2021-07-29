package se.uu.ebc.luntan.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import se.uu.ebc.luntan.entity.EconomyDocGrant;


@Repository
@Transactional(readOnly = true)
public interface EDGRepo extends JpaRepository<EconomyDocGrant, Long>, JpaSpecificationExecutor<EconomyDocGrant>{

 
}
