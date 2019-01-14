package com.voting.repository.impl;

import com.voting.model.Resto;
import com.voting.repository.RestoRepository;
import com.voting.repository.impl.crud.CrudRestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RestoRepositoryImpl implements RestoRepository {

    @Autowired
    private CrudRestoRepository crudRestoRepository;

    @Override
    @Transactional
    public Resto save(Resto resto) {
        return crudRestoRepository.save(resto);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return crudRestoRepository.delete(id) != 0;
    }

    @Override
    public Resto get(int id) {
        return crudRestoRepository.findById(id);
    }

    @Override
    public Resto getByName(String name) {
        return crudRestoRepository.getByName(name);
    }

    @Override
    public List<Resto> getAll() {
        return crudRestoRepository.getAll();
    }
}
