package com.psn_cloud.psiconotes.repositories;

import com.psn_cloud.psiconotes.domain.Psicologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PsicologoRepository extends JpaRepository<Psicologo, UUID> {

    Optional<Psicologo> findByCrp(String crp);
}
