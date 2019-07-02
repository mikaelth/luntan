package se.uu.ebc.luntan.security;

import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
public interface UserRepo extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>{

	@Query("SELECT u FROM User AS u WHERE u.username = ?1")
	public User findUserByUsername(String username);

//    public User findById(Long id);   

}
