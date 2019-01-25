package com.voting.service;

import com.voting.TestUtil;
import com.voting.model.DailyMenu;
import com.voting.model.DailyMenuDish;
import com.voting.model.Resto;
import com.voting.testdata.DishTestData;
import com.voting.testdata.RestoTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

import static com.voting.testdata.DailyMenuTestData.TEST_DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class DailyMenuServiceTest extends AbstractServiceTest{

    @Autowired
    private DailyMenuService service;

    @Autowired
    private RestoService restoService;

    @Autowired
    private DishService dishService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("daily_menu").clear();
    }


    @Test
    public void getByDate() throws ParseException {
        List<DailyMenu> dm = service.getByDate(TEST_DATE);
        assertEquals(dm.size(), 3);
    }

    @Test
    @Transactional
    public void create() throws ParseException {
        Resto resto = restoService.get(TestUtil.getByName(RestoTestData.restos, "Ресторан 4").getId());

        DailyMenu dm = new DailyMenu();

        dm.setDate(TEST_DATE);
        dm.setResto(resto);

        DailyMenuDish dmd = new DailyMenuDish();
        dmd.setDish(dishService.get(TestUtil.getByName(DishTestData.dishes, "Вареники").getId()));
        dm.addDailyMenuDish(dmd);

        dmd = new DailyMenuDish();
        dmd.setDish(dishService.get(TestUtil.getByName(DishTestData.dishes, "Рагу").getId()));
        dm.addDailyMenuDish(dmd);

        service.create(dm);

        List<DailyMenu> dailyMenus = service.getByDate(TEST_DATE);
        assertEquals(dailyMenus.size(), 4);
    }

    @Test
    @Transactional
    public void update() {
        DailyMenu dailyMenu = service.get(100040);
        dailyMenu.setResto(restoService.get(100005));
        DailyMenuDish dmd = new DailyMenuDish();
        dmd.setDish(dishService.get(100012));
        dailyMenu.addDailyMenuDish(dmd);
        service.update(dailyMenu);
        DailyMenu dmUpdated = service.get(100040);
        assertEquals(dailyMenu.getDmDishes().size(), dmUpdated.getDmDishes().size());
        //assertThat(dmUpdated).isEqualToComparingFieldByField(dailymenu);
    }

    @Test
    @Transactional
    public void delete() throws ParseException {
        service.delete(100039);
        List<DailyMenu> dmSet = service.getByDate(TEST_DATE);
        assertEquals(dmSet.size(), 2);
    }

    @Test
    public void get() {
        DailyMenu dailyMenu = service.get(100038);
        assertEquals(dailyMenu.getResto(), TestUtil.getById(RestoTestData.restos, 100003));
    }

    @Test
    public void getAll() {
        List<DailyMenu> dailyMenus = service.getAll();
        assertEquals(dailyMenus.size(), 9);
    }


}