package com.psn_cloud.psiconotes.repositories;

import com.psn_cloud.psiconotes.domain.Disponibilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, UUID> {

    long countByPsicologoId(UUID psicologoId);

    boolean existsByPsicologoIdAndCodigoDia(UUID psicologoId, Integer codigoDia);
}
