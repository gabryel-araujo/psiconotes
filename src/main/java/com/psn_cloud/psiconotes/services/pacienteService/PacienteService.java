package com.psn_cloud.psiconotes.services.pacienteService;

import com.psn_cloud.psiconotes.domain.Paciente;
import com.psn_cloud.psiconotes.services.generics.CrudService;

import java.util.UUID;

public interface PacienteService extends CrudService<Paciente, UUID> {

    Paciente buscarPorNacionalId(String nacionalId);
}
