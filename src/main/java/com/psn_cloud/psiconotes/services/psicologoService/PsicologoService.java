package com.psn_cloud.psiconotes.services.psicologoService;

import com.psn_cloud.psiconotes.domain.Psicologo;
import com.psn_cloud.psiconotes.services.generics.CrudService;

import java.util.UUID;

public interface PsicologoService extends CrudService<Psicologo, UUID> {

    Psicologo buscarPorCrp(String crp);

}
