package com.rahadyan.rms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rahadyan.rms.model.Role;
import com.rahadyan.rms.model.RoleName;

/**
 * 
 * @author Rahadyan_W995
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleName roleName);
}
