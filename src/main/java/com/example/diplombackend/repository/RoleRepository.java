package com.example.diplombackend.repository;

import java.util.Optional;

import com.example.diplombackend.model.ERole;
import com.example.diplombackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
