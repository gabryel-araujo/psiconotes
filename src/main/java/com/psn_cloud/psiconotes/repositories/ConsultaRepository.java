package com.psn_cloud.psiconotes.repositories;

import com.psn_cloud.psiconotes.domain.Consulta;
import com.psn_cloud.psiconotes.enums.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {

    List<Consulta> findByPacienteId(UUID id);
    List<Consulta> findByPsicologoId(UUID id);
    boolean existsByPsicologoIdAndStatusNot(UUID id, StatusConsulta status);
}
