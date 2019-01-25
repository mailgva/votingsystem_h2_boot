package com.voting.repository.impl;

import com.voting.model.DailyMenu;
import com.voting.model.DailyMenuDish;
import com.voting.model.Dish;
import com.voting.model.Resto;
import com.voting.repository.DailyMenuRepository;
import com.voting.repository.impl.crud.CrudDailyMenuRepository;
import com.voting.repository.impl.crud.CrudDishRepository;
import com.voting.repository.impl.crud.CrudRestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public class DailyMenuRepositoryImpl implements DailyMenuRepository {
    private final int COUNT_MENU_PER_DAY_FOR_RESTAURANT = 5;

    @Autowired
    private CrudDailyMenuRepository crudDailyMenuRepository;

    @Autowired
    private CrudRestoRepository crudRestoRepository;

    @Autowired
    private CrudDishRepository crudDishRepository;

    @Override
    @Transactional
    public DailyMenu save(DailyMenu dailyMenu) {
        return crudDailyMenuRepository.save(dailyMenu);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return crudDailyMenuRepository.delete(id) != 0;
    }

    @Override
    public DailyMenu get(int id) {
        return crudDailyMenuRepository.findById(id);
    }

    @Override
    public List<DailyMenu> getByDate(LocalDate date) {
        return crudDailyMenuRepository.getByDate(date);
    }

    @Override
    public List<DailyMenu> getByNameResto(String nameResto) {
        return null; // crudDailyMenuRepository.findAllByNameResto(nameResto);
    }

    @Override
    public List<DailyMenu> getAll() {
        return crudDailyMenuRepository.findAll(); // crudDailyMenuRepository.getAllOrderByDateDescAndOrderByNameRestoAsc();
    }

    @Override
    @Transactional
    public void deleteByDate(LocalDate date) {
        crudDailyMenuRepository.deleteByDate(date);
    }

    @Override
    @Transactional
    public void generateDailyMenu(LocalDate date) {
        //crudDailyMenuRepository.generateDailyMenu(date, date);
        List<Resto> restos = crudRestoRepository.getAll();

        for (Resto resto : restos) {
            DailyMenu dailyMenu = new DailyMenu(date, resto);

            List<Dish> dishes = crudDishRepository.findAll();
            Set<Dish> tmpDishes = new HashSet<>();

            while (tmpDishes.size() < COUNT_MENU_PER_DAY_FOR_RESTAURANT) {
                tmpDishes.add(dishes.get((int) (Math.random() * dishes.size())));
            }
            for (Dish dish : tmpDishes) {
                dailyMenu.addDailyMenuDish(new DailyMenuDish(dish));
            }
            save(dailyMenu);
        }

    }
}
