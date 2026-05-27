package com.psn_cloud.psiconotes.dtos;

import com.psn_cloud.psiconotes.enums.Convenio;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultaDto(
        @NotNull(message = "Informe o psicólogo") UUID psicologoId,
        @NotNull(message = "Informe o paciente") UUID pacienteId,
        @NotNull(message = "Informe a data e hora de início") LocalDateTime dataHoraInicio,
        @NotNull(message = "Informe a data e hora de fim") LocalDateTime dataHoraFim,
        @NotNull(message = "Informe o valor da consulta") BigDecimal valor,
        Convenio convenio,
        String observacoes
) {}