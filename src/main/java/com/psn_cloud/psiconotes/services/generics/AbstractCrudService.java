package com.psn_cloud.psiconotes.services.generics;

import com.psn_cloud.psiconotes.exceptions.RecursoNaoEncontradoException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractCrudService<T, ID> implements CrudService<T, ID>{

    protected abstract JpaRepository<T, ID> getRepository();

    protected abstract String getNomeEntidade();

    @Override
    public T criar(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T buscarPorId(ID id) {
        return getRepository().findById(id)
                .orElseThrow(()->new RecursoNaoEncontradoException(
                        getNomeEntidade() +" não encontrado com id:" + id));
    }

    @Override
    public List<T> listarTodos() {
        return getRepository().findAll();
    }

    @Override
    public T atualizar(ID id, T entity) {
        buscarPorId(id);
        return getRepository().save(entity);
    }

    @Override
    public void remover(ID id) {
        buscarPorId(id);
        getRepository().deleteById(id);
    }
}
