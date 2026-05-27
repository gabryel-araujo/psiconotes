package com.psn_cloud.psiconotes.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record PsicologoDto(
        @NotBlank(message = "O campo nome não pode ser vazio") String nome,
        @NotBlank(message = "O CPF/CNPJ precisa ser preenchido")String nacionalId,
        @NotBlank(message = "O CRP deve ser preenchido") String crp,
        String especialidade,
        String abordagem,
        LocalDate dataNascimento,
        @NotBlank(message = "O telefone não pode ser vazio") String telefone,
        @NotBlank(message = "O email não pode ser vazio") String email,
        Boolean ativo
) {
    
}
