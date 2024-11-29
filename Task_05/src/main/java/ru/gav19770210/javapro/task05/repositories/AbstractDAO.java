package ru.gav19770210.javapro.task05.repositories;

import java.util.List;
import java.util.Optional;

public interface AbstractDAO<T, ID> {
    Optional<T> findById(ID id);

    List<T> findAll();

    T create(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(ID id);
}
