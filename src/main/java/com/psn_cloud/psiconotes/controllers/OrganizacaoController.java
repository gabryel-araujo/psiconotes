package com.psn_cloud.psiconotes.controllers;

import com.psn_cloud.psiconotes.domain.Organizacao;
import com.psn_cloud.psiconotes.services.tenant.TenantProvisioningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas")
public class OrganizacaoController {

    @Autowired
    private TenantProvisioningService tenantProvisioningService;

    // DTO simplificado localmente para nao criar um arquivo so pra isso, mas o ideal seria ter um em dtos/
    public record NovaOrganizacaoDto(String nome) {}

    @PostMapping
    public ResponseEntity<Organizacao> criarEmpresa(@RequestBody NovaOrganizacaoDto dto) {
        Organizacao organizacaoCriada = tenantProvisioningService.provisionNewTenant(dto.nome());
        return ResponseEntity.ok(organizacaoCriada);
    }
}
