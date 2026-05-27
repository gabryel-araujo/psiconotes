package com.psn_cloud.psiconotes.services.disponibilidade;

import com.psn_cloud.psiconotes.domain.Disponibilidade;
import com.psn_cloud.psiconotes.domain.Psicologo;
import com.psn_cloud.psiconotes.exceptions.RecursoNaoEncontradoException;
import com.psn_cloud.psiconotes.exceptions.RegraDeNegocioException;
import com.psn_cloud.psiconotes.repositories.DisponibilidadeRepository;
import com.psn_cloud.psiconotes.repositories.PsicologoRepository;
import com.psn_cloud.psiconotes.services.generics.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DisponibilidadeServiceImpl extends AbstractCrudService<Disponibilidade, UUID> implements DisponibilidadeService {

    @Autowired
    private final DisponibilidadeRepository disponibilidadeRepository;

    @Autowired
    private final PsicologoRepository psicologoRepository;

    @Override
    protected String getNomeEntidade() {
        return "Disponibilidade";
    }

    @Override
    protected JpaRepository<Disponibilidade, UUID> getRepository() {
        return disponibilidadeRepository;
    }

    @Override
    public Disponibilidade criar(Disponibilidade disponibilidade) {
        Psicologo psicologo = psicologoRepository.findById(disponibilidade.getPsicologo().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Psicólogo não encontrado"));

        if (!psicologo.getAtivo()) {
            throw new RegraDeNegocioException("Somente psicólogos ativos podem possuir disponibilidade");
        }

        if(disponibilidade.getCodigoDia() < 1 || disponibilidade.getCodigoDia() > 7){
            throw new RegraDeNegocioException("Código do dia inválido. Escolha um número entre 1 e 7");
        }

        if(disponibilidadeRepository.countByPsicologoId(disponibilidade.getPsicologo().getId()) >= 7) {
            throw new RegraDeNegocioException("Só é possível adicionar uma disponibilidade para cada dia da semana");
        }

        if(disponibilidadeRepository.existsByPsicologoIdAndCodigoDia(disponibilidade.getPsicologo().getId(), disponibilidade.getCodigoDia())){
            throw new RegraDeNegocioException("Psicólogo já possui disponibilidade cadastrada para esse dia");
        }

        return disponibilidadeRepository.save(disponibilidade);
    }
}
