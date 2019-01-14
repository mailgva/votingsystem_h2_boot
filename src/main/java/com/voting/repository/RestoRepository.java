package com.voting.repository;

import com.voting.model.Resto;

import java.util.List;

public interface RestoRepository {
    Resto save(Resto resto);

    // false if not found
    boolean delete(int id);

    // null if not found
    Resto get(int id);

    // null if not found
    Resto getByName(String name);

    List<Resto> getAll();
}
