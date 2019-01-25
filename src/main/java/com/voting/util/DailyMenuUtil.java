package com.voting.util;

import com.voting.model.*;
import com.voting.to.DailyMenuTo;
import com.voting.to.DailyRestoMenuTo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DailyMenuUtil {

    private DailyMenuUtil(){}

    public static DailyRestoMenuTo asTo(DailyMenu dailyMenu) {
        List<Integer> listDishesId = dailyMenu.getDmDishes().stream()
                .map(dailyMenuDish -> dailyMenuDish.getDish().getId())
                .collect(Collectors.toList());

        return new DailyRestoMenuTo(dailyMenu.getDate(), dailyMenu.getResto().getId(), listDishesId);
    }



    public static List<DailyMenuTo> convertToDailyMenuTo(LocalDate date, List<DailyMenu> dailyMenus, Vote vote) {
        Map<Resto, List<Dish>> map = dailyMenus.stream()
                .collect(Collectors.toMap(DailyMenu::getResto, DailyMenu::getDishes)
                );

        List<Resto> restos = map.keySet().stream()
                .sorted(Comparator.comparing(AbstractNamedEntity::getName))
                .collect(Collectors.toList());

        map.entrySet().stream()
                .forEach(m -> {
                    restos.get(restos.indexOf(m.getKey())).setDishes(m.getValue());
                });

        return restos.stream()
                .map(resto -> new DailyMenuTo(date, resto,
                        (vote == null ? 0 : vote.getId()),
                        (vote == null ? false : resto.getId().compareTo(vote.getResto().getId()) == 0) ))
                .collect(Collectors.toList());
    }

}
