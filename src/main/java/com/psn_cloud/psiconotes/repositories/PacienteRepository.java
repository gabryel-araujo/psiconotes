package com.psn_cloud.psiconotes.repositories;

import com.psn_cloud.psiconotes.domain.Paciente;
import com.psn_cloud.psiconotes.domain.Psicologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, UUID> {

    Optional<Paciente> findByNacionalId(String nacionalId);
}
