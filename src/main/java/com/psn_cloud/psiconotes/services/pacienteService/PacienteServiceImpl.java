package com.psn_cloud.psiconotes.services.pacienteService;

import com.psn_cloud.psiconotes.domain.Paciente;
import com.psn_cloud.psiconotes.exceptions.RecursoDuplicadoexception;
import com.psn_cloud.psiconotes.exceptions.RecursoNaoEncontradoException;
import com.psn_cloud.psiconotes.repositories.PacienteRepository;
import com.psn_cloud.psiconotes.services.generics.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl extends AbstractCrudService<Paciente, UUID> implements PacienteService{

    private final PacienteRepository pacienteRepository;

    @Override
    protected String getNomeEntidade() {
        return "Paciente";
    }

    @Override
    protected JpaRepository<Paciente, UUID> getRepository() {
        return pacienteRepository;
    }

    @Override
    public Paciente criar(Paciente paciente){
        pacienteRepository.findByNacionalId(paciente.getNacionalId())
                .ifPresent(p->{
                    throw new RecursoDuplicadoexception("Paciente com o CPF/CNPJ "+ paciente.getNacionalId() + " já cadastrado na base de dados");
                });
        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente buscarPorNacionalId(String nacionalId) {
        return pacienteRepository.findByNacionalId(nacionalId)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Paciente não encontrado com o CFP/CNPJ:"+nacionalId));
    }
}
