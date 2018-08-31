package se.uu.ebc.luntan.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import se.uu.ebc.luntan.security.UserRole;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole>{
}
