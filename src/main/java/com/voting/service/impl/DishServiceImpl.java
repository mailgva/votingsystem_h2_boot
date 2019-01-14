package com.voting.service.impl;

import com.voting.model.Dish;
import com.voting.repository.DishRepository;
import com.voting.service.DishService;
import com.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository repository;

    @CacheEvict(value = "dishes", allEntries = true)
    @Override
    public Dish create(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Dish get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public List<Dish> getByName(String name) {
        Assert.notNull(name, "name must not be null");
        return repository.getByName(name);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    @Override
    public void update(Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(repository.save(dish), dish.getId());
    }

    @Cacheable("dishes")
    @Override
    public List<Dish> getAll() {
        return repository.getAll();
    }

    @Override
    public Dish getByNameAndPrice(String name, double price) {
        return repository.getByNameAndPrice(name, price);
    }
}
