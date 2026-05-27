package com.psn_cloud.psiconotes.services.disponibilidade;

import com.psn_cloud.psiconotes.domain.Disponibilidade;
import com.psn_cloud.psiconotes.domain.Psicologo;
import com.psn_cloud.psiconotes.exceptions.RecursoNaoEncontradoException;
import com.psn_cloud.psiconotes.exceptions.RegraDeNegocioException;
import com.psn_cloud.psiconotes.repositories.DisponibilidadeRepository;
import com.psn_cloud.psiconotes.repositories.PsicologoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DisponibilidadeServiceImplTest {

    @Mock
    private DisponibilidadeRepository disponibilidadeRepository;

    @Mock
    private PsicologoRepository psicologoRepository;

    @InjectMocks
    private DisponibilidadeServiceImpl disponibilidadeServiceImpl;

    private Psicologo psicologoAtivo;
    private Disponibilidade disponibilidade;

    @BeforeEach
    void setUp() {
        //mock de dados do psicologo de teste
        psicologoAtivo = new Psicologo();
        psicologoAtivo.setId(UUID.randomUUID());
        psicologoAtivo.setAtivo(true);
        psicologoAtivo.setNome("Mariana Araújo");
        psicologoAtivo.setCrp("123456789");
        psicologoAtivo.setNacionalId("123456789");
        psicologoAtivo.setTelefone("123456789");
        psicologoAtivo.setEmail("psicologo@email.com");

        //mock de dados da disponibilidade do psicologo de teste
        disponibilidade = new Disponibilidade();
        disponibilidade.setPsicologo(psicologoAtivo);
        disponibilidade.setId(UUID.randomUUID());
        disponibilidade.setCodigoDia(1);
        disponibilidade.setDiaSemana("Segunda-Feira");
        disponibilidade.setHoraInicio(LocalTime.of(8, 0));
        disponibilidade.setHoraFim(LocalTime.of(18, 0));
        disponibilidade.setAtivo(true);
    }

    @Test
    void deve_criar_com_sucesso() {
        //arrange
        when(psicologoRepository.findById(psicologoAtivo.getId())).thenReturn(Optional.of(psicologoAtivo));
        when(disponibilidadeRepository.countByPsicologoId(psicologoAtivo.getId())).thenReturn(0L);
        when(disponibilidadeRepository.existsByPsicologoIdAndCodigoDia(psicologoAtivo.getId(), disponibilidade.getCodigoDia())).thenReturn(false);
        when(disponibilidadeRepository.save(disponibilidade)).thenReturn(disponibilidade);

        //act
        Disponibilidade resultado = disponibilidadeServiceImpl.criar(disponibilidade);

        //assert
        assertThat(resultado).isNotNull();
        verify(disponibilidadeRepository).save(disponibilidade);
    }

    @Test
    void deve_lancar_excecao_quando_psicolgo_nao_encontrado() {
        //arrange
        when(psicologoRepository.findById(psicologoAtivo.getId())).thenReturn(Optional.empty());

        //act & assert
        assertThatThrownBy(() -> disponibilidadeServiceImpl.criar(disponibilidade))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessage("Psicólogo não encontrado");
    }

    @Test
    void deve_lancar_excecao_quando_psicologo_inativo() {
        //arrange
        psicologoAtivo.setAtivo(false);

        when(psicologoRepository.findById(psicologoAtivo.getId())).thenReturn(Optional.of(psicologoAtivo));

        //act & assert
        assertThatThrownBy(() -> disponibilidadeServiceImpl.criar(disponibilidade))
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessage("Somente psicólogos ativos podem possuir disponibilidade");
    }

    @Test
    void deve_lancar_excecao_quando_codigo_dia_for_invalido() {
        //arrange
        disponibilidade.setCodigoDia(8);

        when(psicologoRepository.findById(psicologoAtivo.getId())).thenReturn(Optional.of(psicologoAtivo));

        //act & assert
        assertThatThrownBy(() -> disponibilidadeServiceImpl.criar(disponibilidade))
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessage("Código do dia inválido. Escolha um número entre 1 e 7");

    }

    @Test
    void deve_lancar_excecao_quando_numero_de_disponibilidades_atingir_sete() {
        //arrange
        when(psicologoRepository.findById(psicologoAtivo.getId())).thenReturn(Optional.of(psicologoAtivo));
        when(disponibilidadeRepository.countByPsicologoId(psicologoAtivo.getId())).thenReturn(7L);

        //act & assert
        assertThatThrownBy(() -> disponibilidadeServiceImpl.criar(disponibilidade))
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessage("Só é possível adicionar uma disponibilidade para cada dia da semana");
    }

    @Test
    void deve_lancar_excecao_quando_disponibilidade_ja_cadastrada(){
        //arrange
        when(psicologoRepository.findById(psicologoAtivo.getId())).thenReturn(Optional.of(psicologoAtivo));
        when(disponibilidadeRepository.countByPsicologoId(psicologoAtivo.getId())).thenReturn(3L);
        when(disponibilidadeRepository.existsByPsicologoIdAndCodigoDia(psicologoAtivo.getId(), disponibilidade.getCodigoDia())).thenReturn(true);

        //act & assert
        assertThatThrownBy(() -> disponibilidadeServiceImpl.criar(disponibilidade))
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessage("Psicólogo já possui disponibilidade cadastrada para esse dia");
    }

}