package net.dzioba.petclinic.services;

import net.dzioba.petclinic.model.BaseEntity;

import java.util.Set;

public interface CrudService<T extends BaseEntity> {

    Set<T> findAll();

    T findById(Long id);

    T save(T object);

    void deleteById(Long id);

    void delete(T object);
}
