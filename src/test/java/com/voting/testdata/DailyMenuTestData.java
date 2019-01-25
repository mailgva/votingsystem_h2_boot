package com.voting.testdata;

import com.voting.TestUtil;
import com.voting.model.DailyMenu;
import com.voting.model.DailyMenuDish;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.voting.TestUtil.readFromJsonMvcResult;
import static com.voting.TestUtil.readListFromJsonMvcResult;
import static com.voting.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class DailyMenuTestData {
    public static final LocalDate TEST_DATE = LocalDate.of(2018,11,21);

    public static int dailyMenuId = START_SEQ + 2 + 5 + 30; // 2 - users, 5 - restaurants, 30 - dishes

    public static int dailyMenuDishId = START_SEQ + 2 + 5 + 30 + 9; // 2 - users, 5 - restaurants, 30 - dishes, 9 - dailymenu

    private static final List<DailyMenuDish> DAILY_MENU_DISHES1 = List.of(
            new DailyMenuDish(dailyMenuDishId++, TestUtil.getByName(DishTestData.dishes, "Салат Оливье")),
            new DailyMenuDish(dailyMenuDishId++, TestUtil.getByName(DishTestData.dishes, "Салат Столичный"))
    );

    private static final List<DailyMenuDish> DAILY_MENU_DISHES2 = List.of(
            new DailyMenuDish(dailyMenuDishId++, TestUtil.getByName(DishTestData.dishes, "Салат Цезарь")),
            new DailyMenuDish(dailyMenuDishId++, TestUtil.getByName(DishTestData.dishes, "Стейк"))
    );

    private static final List<DailyMenuDish> DAILY_MENU_DISHES3 = List.of(
            new DailyMenuDish(dailyMenuDishId++, TestUtil.getByName(DishTestData.dishes, "Шашлык")),
            new DailyMenuDish(dailyMenuDishId++, TestUtil.getByName(DishTestData.dishes, "Салат Шефский"))
    );
    
    public static final List<DailyMenu> DAILY_MENUS = List.of(
            new DailyMenu(dailyMenuId++, TEST_DATE, TestUtil.getByName(RestoTestData.restos, "Ресторан 1"), DAILY_MENU_DISHES1),
            new DailyMenu(dailyMenuId++, TEST_DATE, TestUtil.getByName(RestoTestData.restos, "Ресторан 2"), DAILY_MENU_DISHES2),
            new DailyMenu(dailyMenuId++, TEST_DATE, TestUtil.getByName(RestoTestData.restos, "Ресторан 3"), DAILY_MENU_DISHES3)
    );
    

    private DailyMenuTestData() {
    }

    public static void assertMatch(DailyMenu actual, DailyMenu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected,  "dmDishes","date");
        //assertThat(actual).isEqualTo(expected);
    }


    public static void assertMatch(Iterable<DailyMenu> actual, DailyMenu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<DailyMenu> actual, Iterable<DailyMenu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dmDishes","date").isEqualTo(expected);
    }


    public static ResultMatcher getDailyMenuMatcher(DailyMenu... expected) {
        return result -> assertMatch(
                readListFromJsonMvcResult(result, DailyMenu.class).stream().sorted(Comparator.comparing(o -> o.getResto().getName())).collect(Collectors.toList()),
                List.of(expected));
    }

    public static ResultMatcher getDailyMenuMatcher(DailyMenu expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, DailyMenu.class), expected);
    }

}
