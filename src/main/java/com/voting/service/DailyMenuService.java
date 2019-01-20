package com.voting.service;

import com.voting.model.DailyMenu;
import com.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface DailyMenuService {
    DailyMenu create(DailyMenu dailyMenu);

    void update(DailyMenu dailyMenu);

    void delete(int id) throws NotFoundException;

    DailyMenu get(int id) throws NotFoundException;

    List<DailyMenu> getByDate(LocalDate date) ;

    List<DailyMenu> getAll();

    void generateDailyMenu(LocalDate date);
}
