package com.psn_cloud.psiconotes.services.psicologoService;

import com.psn_cloud.psiconotes.domain.Psicologo;
import com.psn_cloud.psiconotes.exceptions.RecursoDuplicadoexception;
import com.psn_cloud.psiconotes.exceptions.RecursoNaoEncontradoException;
import com.psn_cloud.psiconotes.repositories.PsicologoRepository;
import com.psn_cloud.psiconotes.services.generics.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PsicologoServiceImpl extends AbstractCrudService<Psicologo, UUID> implements PsicologoService {

    private final PsicologoRepository psicologoRepository;

    @Override
    protected String getNomeEntidade() {
        return "Psicólogo";
    }

    @Override
    protected JpaRepository<Psicologo, UUID> getRepository() {
        return psicologoRepository;
    }

    @Override
    public Psicologo criar(Psicologo psicologo){
        psicologoRepository.findByCrp(psicologo.getCrp())
                .ifPresent(p->{
                    throw new RecursoDuplicadoexception("Psicólogo já cadastrado na base de dados");
                });
        return psicologoRepository.save(psicologo);
    }

    @Override
    public Psicologo buscarPorCrp(String crp) {
        return psicologoRepository.findByCrp(crp).orElseThrow(()->
                new RecursoNaoEncontradoException("Psicologo não encontrado com o crp:"+crp));
    }
}
