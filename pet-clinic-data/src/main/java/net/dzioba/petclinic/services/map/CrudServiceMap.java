package net.dzioba.petclinic.services.map;

import net.dzioba.petclinic.services.CrudService;

import java.util.*;

class CrudServiceMap<T, ID> implements CrudService<T, ID> {

    Map<ID, T> map = new HashMap<>();

    @Override
    public Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    @Override
    public T findById(ID id) {
        Objects.requireNonNull(id);
        return map.get(id);
    }

    @Override
    public T save(ID id, T object) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(object);

        return map.put(id, object);
    }

    @Override
    public void deleteById(ID id) {
        Objects.requireNonNull(id);
        map.remove(id);
    }

    @Override
    public void delete(T object) {
        Objects.requireNonNull(object);
        map.values().removeIf(value -> value.equals(object));
    }
}
