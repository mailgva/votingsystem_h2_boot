package com.voting.service.impl;

import com.voting.model.DailyMenu;
import com.voting.repository.DailyMenuRepository;
import com.voting.service.DailyMenuService;
import com.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DailyMenuServiceImpl implements DailyMenuService {

    private final DailyMenuRepository repository;

    @Autowired
    public DailyMenuServiceImpl(DailyMenuRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "daily_menu", allEntries = true)
    @Override
    public DailyMenu create(DailyMenu dailyMenu) {
        Assert.notNull(dailyMenu, "dailymenu must not be null");
        return repository.save(dailyMenu);
    }

    @CacheEvict(value = "daily_menu", allEntries = true)
    @Override
    public void update(DailyMenu dailyMenu) {
        Assert.notNull(dailyMenu, "dailymenu must not be null");
        checkNotFoundWithId(repository.save(dailyMenu), dailyMenu.getId());
    }

    @CacheEvict(value = "daily_menu", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public DailyMenu get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    //@Cacheable("daily_menu")
    @Override
    public List<DailyMenu> getByDate(LocalDate date) {
        return repository.getByDate(date);
    }

    @Cacheable("daily_menu")
    @Override
    public List<DailyMenu> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "daily_menu", allEntries = true)
    @Override
    public void generateDailyMenu(LocalDate date) {
        repository.deleteByDate(date);
        repository.generateDailyMenu(date);
    }
}
