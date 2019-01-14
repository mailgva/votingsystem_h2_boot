package com.voting.repository;

import com.voting.model.DailyMenu;

import java.util.Date;
import java.util.List;

public interface DailyMenuRepository {
    DailyMenu save(DailyMenu dailyMenu);

    // false if not found
    boolean delete(int id);

    // null if not found
    DailyMenu get(int id);

    // null if not found
    List<DailyMenu> getByDate(Date date);

    List<DailyMenu> getByNameResto(String nameResto);

    List<DailyMenu> getAll();

    void deleteByDate(Date date);

    void generateDailyMenu(Date date);
}
