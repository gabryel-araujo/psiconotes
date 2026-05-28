package com.psn_cloud.psiconotes.services.consulta;

import com.psn_cloud.psiconotes.domain.Paciente;
import com.psn_cloud.psiconotes.domain.Psicologo;
import com.psn_cloud.psiconotes.repositories.ConsultaRepository;
import com.psn_cloud.psiconotes.repositories.PacienteRepository;
import com.psn_cloud.psiconotes.repositories.PsicologoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private PsicologoRepository psicologoRepository;

    @Mock
    private ConsultaService consultaService;

    private Psicologo psicologo;
    private Paciente paciente;

    @BeforeEach
    void setUp() {
        //instancia de paciente
        paciente = new Paciente();
        paciente.setId(UUID.randomUUID());
        paciente.setNome("Gabryel");
        paciente.setAtivo(true);

        //instancia de psicologo
        psicologo = new Psicologo();
        psicologo.setId(UUID.randomUUID());
        psicologo.setNome("Mariana");
        psicologo.setAtivo(true);
    }

    @Test
    void cadastrarConsulta() {

    }

    @Test
    void confirmarConsulta() {
    }

    @Test
    void cancelarConsulta() {
    }

    @Test
    void finalizarConsulta() {
    }

    @Test
    void marcarFalta() {
    }
}