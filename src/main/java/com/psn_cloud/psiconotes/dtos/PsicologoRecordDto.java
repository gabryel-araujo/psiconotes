package com.psn_cloud.psiconotes.dtos;

import jakarta.validation.constraints.NotBlank;

public record PsicologoRecordDto(
        @NotBlank(message = "O campo nome não pode ser vazio") String nome,
        @NotBlank(message = "O CPF/CNPJ precisa ser preenchido")String nacionalId,
        @NotBlank(message = "O CRP deve ser preenchido") String crp
) {}
