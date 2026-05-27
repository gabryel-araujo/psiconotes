package com.psn_cloud.psiconotes.repositories;

import com.psn_cloud.psiconotes.domain.Consulta;
import com.psn_cloud.psiconotes.enums.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {

    List<Consulta> findByPacienteId(UUID id);
    List<Consulta> findByPsicologoId(UUID id);

    @Query("""
        SELECT COUNT(c) > 0 from Consulta c
        WHERE c.psicologo.id = :psicologoID AND
        c.status NOT IN ('CANCELADA', 'FALTA') AND
        c.dataHoraInicio < :fim AND
        c.dataHoraFim > :inicio
    """)
    boolean existeConflitoDeHorario(
            @Param("psicologoId") UUID psicologoID,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}
