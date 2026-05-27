package com.psn_cloud.psiconotes.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "disponibilidade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disponibilidade extends ClasseAuditavel{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "psicologo_id", nullable = false)
    private Psicologo psicologo;

    @Column(nullable = false, name = "dia_semana")
    private String diaSemana;

    @Column(nullable = false, name = "codigo_dia")
    private Integer codigoDia;

    @Column(nullable = false, name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(nullable = false, name = "hora_fim")
    private LocalTime horaFim;

    @Column(nullable = false)
    private Boolean ativo;
}
