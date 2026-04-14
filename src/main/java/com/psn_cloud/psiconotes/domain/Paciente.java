package com.psn_cloud.psiconotes.domain;

import com.psn_cloud.psiconotes.enums.Convenio;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente extends ClasseAuditavel{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "nacional_id", unique = true, length = 14, nullable = false)
    private String nacionalId;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(nullable = false, length = 30)
    private String telefone;

    @Column(length = 120, unique = true)
    private String email;

    @Column(name = "contato_emergencia",length = 30)
    private String contatoEmergencia;

    private String observacoes;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private Convenio convenio;

    @Column(name = "responsavel_legal")
    private String responsavelLegal;

    @Column(length = 120)
    private String profissao;

    @Column(name = "estado_civil",length = 120)
    private String estadoCivil;

    @Column(nullable = false)
    private boolean ativo = true;

}
