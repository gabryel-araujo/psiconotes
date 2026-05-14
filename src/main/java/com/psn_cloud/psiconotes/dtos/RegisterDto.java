package com.psn_cloud.psiconotes.dtos;

import com.psn_cloud.psiconotes.enums.Role;

public record RegisterDto(String nome, String email, String senha, Role role, Long organizacaoId) {
}
