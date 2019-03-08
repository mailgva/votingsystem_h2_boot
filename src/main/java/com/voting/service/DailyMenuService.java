package com.voting.service;

import com.voting.model.DailyMenu;
import com.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface DailyMenuService {
    DailyMenu create(DailyMenu dailyMenu);

    void update(DailyMenu dailyMenu);

    void delete(int id) throws NotFoundException;

    void deleteByDate(LocalDate date);

    DailyMenu get(int id) throws NotFoundException;

    List<DailyMenu> getByDate(LocalDate date) ;

    List<DailyMenu> getAll();

    void generateDailyMenu(LocalDate date);
}
