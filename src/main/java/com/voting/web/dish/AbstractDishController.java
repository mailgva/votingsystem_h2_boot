package com.voting.web.dish;

import com.voting.model.Dish;
import com.voting.service.DishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.voting.util.ValidationUtil.assureIdConsistent;
import static com.voting.util.ValidationUtil.checkNew;

public abstract class AbstractDishController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DishService service;

    public List<Dish> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Dish get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public Dish create(Dish dish) {
        log.info("create {}", dish);
        checkNew(dish);
        return service.create(dish);
    }

    public void update(Dish dish, int id) {
        log.info("update {} with id={}", dish, id);
        assureIdConsistent(dish, id);
        service.update(dish);
    }
}
