package com.psn_cloud.psiconotes.domain;

import com.psn_cloud.psiconotes.enums.Convenio;
import com.psn_cloud.psiconotes.enums.StatusConsulta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "consulta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consulta extends ClasseAuditavel{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "psicologo_id", nullable = false)
    private Psicologo psicologo;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(nullable = false, name = "data_hora_inicio")
    private LocalDateTime dataHoraInicio;

    @Column(nullable = false, name = "data_hora_fim")
    private LocalDateTime dataHoraFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private StatusConsulta status;

    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Convenio convenio;

    private String observacoes;
}
