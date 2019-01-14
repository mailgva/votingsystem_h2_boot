package com.voting.service.impl;

import com.voting.model.Resto;
import com.voting.repository.RestoRepository;
import com.voting.service.RestoService;
import com.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.voting.util.ValidationUtil.checkNotFound;
import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestoServiceImpl implements RestoService {

    @Autowired
    private RestoRepository repository;

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public Resto create(Resto resto) {
        Assert.notNull(resto, "resto must not be null");
        return repository.save(resto);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Resto get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public Resto getByName(String name) throws NotFoundException {
        Assert.notNull(name, "name must not be null");
        return checkNotFound(repository.getByName(name), "name=" + name);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void update(Resto resto) {
        Assert.notNull(resto, "resto must not be null");
        checkNotFoundWithId(repository.save(resto), resto.getId());
    }

    @Cacheable("restaurants")
    @Override
    public List<Resto> getAll() {
        return repository.getAll();
    }
}
