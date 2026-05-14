package com.psn_cloud.psiconotes.repositories;

import com.psn_cloud.psiconotes.domain.OrganizacaoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizacaoUsuarioRepository extends JpaRepository<OrganizacaoUsuario, UUID> {
    Optional<OrganizacaoUsuario> findByEmail(String email);
}
