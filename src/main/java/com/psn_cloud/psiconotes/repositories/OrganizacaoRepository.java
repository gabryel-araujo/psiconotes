package com.psn_cloud.psiconotes.repositories;

import com.psn_cloud.psiconotes.domain.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {
}
