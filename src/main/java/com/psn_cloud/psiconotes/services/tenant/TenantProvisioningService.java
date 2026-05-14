package com.psn_cloud.psiconotes.services.tenant;

import com.psn_cloud.psiconotes.domain.Organizacao;
import com.psn_cloud.psiconotes.repositories.OrganizacaoRepository;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class TenantProvisioningService {

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private DataSource dataSource;

    public Organizacao provisionNewTenant(String nomeEmpresa) {
        // 1. Salvar na tabela public.organizacao para gerar o ID sequencial
        Organizacao organizacao = Organizacao.builder().nome(nomeEmpresa).build();
        organizacao = organizacaoRepository.save(organizacao);

        String schemaName = "o" + organizacao.getId();

        // 2. Executar o Flyway apenas neste novo schema
        // O Flyway já cria o schema automaticamente se ele não existir
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas(schemaName)
                .locations("classpath:db/migration/tenants")
                .load();

        flyway.migrate();

        return organizacao;
    }
}
