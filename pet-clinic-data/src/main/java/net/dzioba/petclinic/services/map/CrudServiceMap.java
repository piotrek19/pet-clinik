package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.model.BaseEntity;
import net.dzioba.petclinic.services.CrudService;

import java.util.*;

import static java.util.Objects.requireNonNull;

abstract class CrudServiceMap<T extends BaseEntity> implements CrudService<T> {

    Map<Long, T> map = new HashMap<>();

    @Override
    public Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    @Override
    public T findById(Long id) {
        requireNonNull(id);
        return map.get(id);
    }

    @Override
    public T save(T object){
        requireNonNull(object);
        setIdOf(object);
        map.put(object.getId(), object);
        return object;
    }

    private void setIdOf(T object) {
        if (object.getId() == null){
            Set<Long> keys = map.keySet();
            if (keys.size() != 0) {
                object.setId(Collections.max(keys) + 1L);
            }
            else {
                object.setId(1L);
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        requireNonNull(id);
        map.remove(id);
    }

    @Override
    public void delete(T object) {
        requireNonNull(object);
        map.values().removeIf(value -> value.equals(object));
    }
}
