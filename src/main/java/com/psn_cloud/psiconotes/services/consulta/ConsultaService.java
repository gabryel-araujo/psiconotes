package com.psn_cloud.psiconotes.services.consulta;

import com.psn_cloud.psiconotes.domain.Consulta;
import com.psn_cloud.psiconotes.domain.Paciente;
import com.psn_cloud.psiconotes.domain.Psicologo;
import com.psn_cloud.psiconotes.exceptions.RecursoNaoEncontradoException;
import com.psn_cloud.psiconotes.exceptions.RegraDeNegocioException;
import com.psn_cloud.psiconotes.repositories.ConsultaRepository;
import com.psn_cloud.psiconotes.repositories.PacienteRepository;
import com.psn_cloud.psiconotes.repositories.PsicologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private PsicologoRepository psicologoRepository;

    public List<Consulta> listarConsultas() {
        return consultaRepository.findAll();
    }

    public Consulta consultaPorId(UUID id) {
        return consultaRepository.findById(id).orElse(null);
    }

    public List<Consulta> listarConsultasPorPsicologo(UUID id) {
        return consultaRepository.findByPsicologoId(id);
    }

    public List<Consulta> listarConsultasPorPaciente(UUID id) {
        return consultaRepository.findByPacienteId(id);
    }

    public Consulta cadastrarConsulta(Consulta consulta) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(consulta.getPaciente().getId());
        
        if(pacienteOptional.isEmpty()) {
            throw new RecursoNaoEncontradoException("Paciente não localizado na base de dados");
        } else if (!pacienteOptional.get().isAtivo()) {
            throw new RegraDeNegocioException("O paciente deve ser ativo");
        }

        Optional<Psicologo> psicologoOptional = psicologoRepository.findById(consulta.getPsicologo().getId());
        psicologoOptional.ifPresent(consulta::setPsicologo);

        if(psicologoOptional.isEmpty()) {
            throw new RecursoNaoEncontradoException("Paciente não localizado na base de dados");
        } else if (!psicologoOptional.get().getAtivo()) {
            throw new RegraDeNegocioException("O paciente deve ser ativo");
        }

        //DAQUI PRA BAIXO DEPENDE DA LOGICA DA DISPONIBILIDADE
        return consultaRepository.save(consulta);
    }

}
