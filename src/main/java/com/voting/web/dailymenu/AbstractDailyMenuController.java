package com.voting.web.dailymenu;

import com.voting.model.DailyMenu;
import com.voting.service.DailyMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.voting.util.ValidationUtil.assureIdConsistent;
import static com.voting.util.ValidationUtil.checkNew;

@Controller
public abstract class AbstractDailyMenuController {
    private static final Logger log = LoggerFactory.getLogger(AbstractDailyMenuController.class);

    @Autowired
    private DailyMenuService service;

    public DailyMenu get(int id) {
        log.info("get DailyMenu {} ", id);
        return service.get(id);
    }

    public List<DailyMenu> getAll() {
        log.info("getAll of DailyMenu");
        return service.getAll();
    }

    public void delete(int id) {
        log.info("delete DailyMenu {} ", id);
        service.delete(id);
    }

    public DailyMenu create(DailyMenu dailyMenu) {
        checkNew(dailyMenu);
        log.info("create {}", dailyMenu);
        return service.create(dailyMenu);
    }

    public void update(DailyMenu dailyMenu, int id) {
        assureIdConsistent(dailyMenu, id);
        log.info("update {} ", dailyMenu);
        service.update(dailyMenu);
    }


    public Set<DailyMenu> getByDate(Date date) {
        log.info("get DailyMenu for date {} ", date);
        return service.getByDate(date);
    }

    public void generateDailyMenu(Date date) {
        service.generateDailyMenu(date);
    }

}