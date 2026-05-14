package com.psn_cloud.psiconotes.config.multitenancy;

import com.psn_cloud.psiconotes.domain.Organizacao;
import com.psn_cloud.psiconotes.repositories.OrganizacaoRepository;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class TenantMigrationInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TenantMigrationInitializer.class);

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Iniciando a migração do banco de dados para todos os Tenants existentes...");

        List<Organizacao> organizacoes = organizacaoRepository.findAll();

        if (organizacoes.isEmpty()) {
            logger.info("Nenhuma organização encontrada. Ignorando migrações de tenants.");
            return;
        }

        for (Organizacao organizacao : organizacoes) {
            String schemaName = "o" + organizacao.getId();
            logger.info("Aplicando migrações do Flyway para o schema: {}", schemaName);

            try {
                Flyway flyway = Flyway.configure()
                        .dataSource(dataSource)
                        .schemas(schemaName)
                        .locations("classpath:db/migration/tenants")
                        .load();
                
                flyway.migrate();
                logger.info("Migrações concluídas com sucesso para o schema: {}", schemaName);
            } catch (Exception e) {
                logger.error("Erro ao aplicar migração para o schema: {}", schemaName, e);
                // Evitamos que um erro em um cliente impeça a aplicação inteira de subir ou de atualizar outros clientes.
            }
        }

        logger.info("Todas as migrações de Tenants foram concluídas.");
    }
}
