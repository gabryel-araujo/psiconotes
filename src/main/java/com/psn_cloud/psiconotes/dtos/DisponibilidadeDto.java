package com.psn_cloud.psiconotes.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

public record DisponibilidadeDto(
        @NotNull(message = "Informe o psicólogo") UUID psicologoId,
        @NotNull(message = "Informe o código do dia") Integer codigoDia,
        @NotBlank(message = "Informe o dia da semana") String diaSemana,
        @NotNull(message = "Informe o horário de início") LocalTime horaInicio,
        @NotNull(message = "Informe o horário de fim") LocalTime horaFim
) {}
