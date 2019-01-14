package com.voting.service;

import com.voting.model.Resto;
import com.voting.util.exception.NotFoundException;

import java.util.List;

public interface RestoService {
    Resto create(Resto resto);

    void delete(int id) throws NotFoundException;

    Resto get(int id) throws NotFoundException;

    Resto getByName(String name) throws NotFoundException;

    void update(Resto resto);

    List<Resto> getAll();
}
