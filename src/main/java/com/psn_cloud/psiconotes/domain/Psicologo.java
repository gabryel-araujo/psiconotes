package com.psn_cloud.psiconotes.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "psicologo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Psicologo extends ClasseAuditavel{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "nacional_id", unique = true, length = 14, nullable = false)
    private String nacionalId;

    @Column(unique = true, length = 7, nullable = false)
    private String crp;

    @Column(length = 100)
    private String especialidade;

    @Column(length = 100)
    private String abordagem;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(nullable = false, length = 30)
    private String telefone;

    @Column(length = 120, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean ativo = true;
}
