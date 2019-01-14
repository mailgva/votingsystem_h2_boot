package com.voting.service;

import com.voting.model.Dish;
import com.voting.util.exception.NotFoundException;

import java.util.List;

public interface DishService {
    Dish create(Dish dish);

    void delete(int id) throws NotFoundException;

    Dish get(int id) throws NotFoundException;

    List<Dish> getByName(String name) throws NotFoundException;

    void update(Dish dish);

    List<Dish> getAll();

    Dish getByNameAndPrice(String name, double price);
}
