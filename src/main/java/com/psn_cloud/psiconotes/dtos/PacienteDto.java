package com.psn_cloud.psiconotes.dtos;

import com.psn_cloud.psiconotes.enums.Convenio;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record PacienteDto(
        @NotBlank(message = "O nome do paciente é obrigatório") String nome,
        @NotBlank(message = "Informe o Cpf/CNPJ do paciente") String nacionalId,
        @NotBlank(message = "Um telefone válido deve ser especificado") String telefone,
        LocalDate dataNascimento,
        String email,
        String contatoEmergencia,
        String observacoes,
        Convenio convenio,
        String responsavelLegal,
        String profissao,
        String estadoCivil,
        Boolean ativo
) {
}
