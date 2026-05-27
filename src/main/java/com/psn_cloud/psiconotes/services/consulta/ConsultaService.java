package com.psn_cloud.psiconotes.services.consulta;

import com.psn_cloud.psiconotes.domain.Consulta;
import com.psn_cloud.psiconotes.domain.Paciente;
import com.psn_cloud.psiconotes.domain.Psicologo;
import com.psn_cloud.psiconotes.enums.StatusConsulta;
import com.psn_cloud.psiconotes.exceptions.RecursoNaoEncontradoException;
import com.psn_cloud.psiconotes.exceptions.RegraDeNegocioException;
import com.psn_cloud.psiconotes.repositories.ConsultaRepository;
import com.psn_cloud.psiconotes.repositories.DisponibilidadeRepository;
import com.psn_cloud.psiconotes.repositories.PacienteRepository;
import com.psn_cloud.psiconotes.repositories.PsicologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
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

    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

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

        if(psicologoOptional.isEmpty()) {
            throw new RecursoNaoEncontradoException("Psicologo não localizado na base de dados");
        } else if (!psicologoOptional.get().getAtivo()) {
            throw new RegraDeNegocioException("O psicologo deve ser ativo");
        }

        psicologoOptional.ifPresent(consulta::setPsicologo);
        Psicologo psicologo = psicologoOptional.get();

        int diaConsulta = consulta.getDataHoraInicio().getDayOfWeek().getValue();

        if (consulta.getDataHoraInicio().isBefore(LocalDateTime.now())) {
            throw new RegraDeNegocioException("Não é possível agendar consulta em data passada");
        }

        if(!disponibilidadeRepository.existsByPsicologoIdAndCodigoDia(psicologo.getId(), diaConsulta)) {
            throw new RegraDeNegocioException("O psicólogo não possui disponibilidade para esse dia");
        }

        if(consultaRepository.existeConflitoDeHorario(psicologo.getId(),consulta.getDataHoraInicio(),consulta.getDataHoraFim())){
            throw new RegraDeNegocioException("O psicólogo está ocupado no horário selecionado");
        }

        //TODO: FALTA IMPLEMENTAR A LOGICA PARA VERIFICAR A DISPONIBILIDADE DO PSICOLOGO

        consulta.setStatus(StatusConsulta.AGENDADA);
        return consultaRepository.save(consulta);
    }

    public Consulta confirmarConsulta(UUID id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Consulta não localizada! Verifique os dados"));

        if(consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new RegraDeNegocioException("Não é possível realizar a confirmação de uma consulta Cancelada");
        }

        consulta.setStatus(StatusConsulta.CONFIRMADA);
        return consultaRepository.save(consulta);
    }

    public Consulta cancelarConsulta(UUID id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Consulta não localizada! Verifique os dados"));

        if(consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new RegraDeNegocioException("A consulta já se encontra cancelada");
        }

        if(consulta.getStatus() == StatusConsulta.FALTA) {
            throw new RegraDeNegocioException("Não é possível cancelar uma consulta marcada como falta");
        }

        consulta.setStatus(StatusConsulta.CANCELADA);
        return consultaRepository.save(consulta);
    }

    public Consulta finalizarConsulta(UUID id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Consulta não localizada! Verifique os dados"));

        if(consulta.getStatus() == StatusConsulta.CONFIRMADA) {
            throw new RegraDeNegocioException("Só é possível finalizar consultas confirmadas. Realize a confirmação e tente novamente");
        }

        consulta.setStatus(StatusConsulta.REALIZADA);
        return consultaRepository.save(consulta);
    }

    public Consulta marcarFalta(UUID id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Consulta não localizada! Verifique os dados"));

        if(consulta.getStatus().equals(StatusConsulta.FALTA)) {
            throw new RegraDeNegocioException("A falta já foi definida para essa consulta");
        }

        if(consulta.getStatus() != StatusConsulta.CONFIRMADA ) {
            throw new RegraDeNegocioException("Só é possível marcar falta para consultas confirmadas");
        }
        consulta.setStatus(StatusConsulta.FALTA);
        return consultaRepository.save(consulta);
    }
}
