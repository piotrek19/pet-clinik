package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.BaseEntity;
import net.dzioba.petclinic.services.CrudService;

import java.util.*;

abstract class CrudServiceMap<T extends BaseEntity> implements CrudService<T> {

    Map<Long, T> map = new HashMap<>();

    @Override
    public Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    @Override
    public T findById(Long id) {
        Objects.requireNonNull(id);
        return map.get(id);
    }

    @Override
    public T save(T object){
        Objects.requireNonNull(object);
        setIdOf(object);
        return map.put(object.getId(), object);
    }

    private void setIdOf(T object) {
        if (object.getId() == null){
            object.setId(map.size()+ 1L);
        }
    }

    @Override
    public void deleteById(Long id) {
        Objects.requireNonNull(id);
        map.remove(id);
    }

    @Override
    public void delete(T object) {
        Objects.requireNonNull(object);
        map.values().removeIf(value -> value.equals(object));
    }
}
