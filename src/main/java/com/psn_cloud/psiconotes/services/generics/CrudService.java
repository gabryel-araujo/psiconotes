package com.psn_cloud.psiconotes.services.generics;


import java.util.List;

public interface CrudService<T, ID> {
    T criar(T entity);
    T buscarPorId(ID id);
    List<T> listarTodos();
    T atualizar (ID id, T entity);
    void remover (ID id);
}
