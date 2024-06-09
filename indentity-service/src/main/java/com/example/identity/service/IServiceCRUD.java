package com.example.identity.service;

import java.util.Collection;

public interface IServiceCRUD<E, ERQ, ERP> {
    Collection<E> getList();

    E findById(String id);

    ERP createEntity(ERQ erq);

    String deleteById(String id);
}
