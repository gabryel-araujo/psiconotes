package com.psn_cloud.psiconotes.config.multitenancy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HibernateMultiTenancyPostProcessor implements BeanPostProcessor {

    private final TenantIdentifierResolver tenantIdentifierResolver;
    private final SchemaMultiTenantConnectionProvider multiTenantConnectionProvider;

    public HibernateMultiTenancyPostProcessor(TenantIdentifierResolver tenantIdentifierResolver, SchemaMultiTenantConnectionProvider multiTenantConnectionProvider) {
        this.tenantIdentifierResolver = tenantIdentifierResolver;
        this.multiTenantConnectionProvider = multiTenantConnectionProvider;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof LocalContainerEntityManagerFactoryBean) {
            LocalContainerEntityManagerFactoryBean factory = (LocalContainerEntityManagerFactoryBean) bean;
            Map<String, Object> jpaProperties = factory.getJpaPropertyMap();
            jpaProperties.put("hibernate.tenant_identifier_resolver", tenantIdentifierResolver);
            jpaProperties.put("hibernate.multi_tenant_connection_provider", multiTenantConnectionProvider);
        }
        return bean;
    }
}
