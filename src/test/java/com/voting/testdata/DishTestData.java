package com.voting.testdata;

import com.voting.model.Dish;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static com.voting.TestUtil.readFromJsonMvcResult;
import static com.voting.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static int dishId = START_SEQ + 2 + 5; // 2 - users, 5 - restaurants

    public static List<Dish> dishes = List.of(
            new Dish(dishId++, "Салат Оливье", 50.0, "pictures/dishes/d0f45cc0-46cc-49ce-995f-bcbc4071427b.jpg"),
            new Dish(dishId++, "Салат Столичный", 40.0, "pictures/dishes/46d394c5-d84c-4080-84c6-6e6a1324ec14.jpg"),
            new Dish(dishId++, "Салат Цезарь", 90.0,"pictures/dishes/592caf2e-55a8-483e-89e2-24c1f19f34d4.jpg"),
            new Dish(dishId++, "Стейк", 150.0, "pictures/dishes/74d3e033-5133-4606-8dd9-fb64f3fd1291.JPG"),
            new Dish(dishId++, "Шашлык", 120.0,"pictures/dishes/shashlik.jpg"),
            new Dish(dishId++, "Салат Шефский", 70.0,"pictures/dishes/0d1a9e02-cedc-4052-b5cb-fc098943d3d1.jpg"),
            new Dish(dishId++, "Салат Греческий особенный", 80.0,"pictures/dishes/ab4f5e29-24c0-4cd9-9994-cedb25070970.jpg"),
            new Dish(dishId++, "Стейк от шефа", 140.0),
            new Dish(dishId++, "Шашлык шея", 130.0),
            new Dish(dishId++, "Запеченные овощи", 110.0),
            new Dish(dishId++, "Пицца", 125.0),
            new Dish(dishId++, "Картошка Фри", 40.0),
            new Dish(dishId++, "Картошка запеченная", 45.0),
            new Dish(dishId++, "Стейк с картошкой", 170.0),
            new Dish(dishId++, "Шашлык телятина", 135.0),
            new Dish(dishId++, "Запеченные овощи в тандыре", 70.0),
            new Dish(dishId++, "Курица Гриль", 105.0),
            new Dish(dishId++, "Сэндвич", 50.0),
            new Dish(dishId++, "Суп Харчо", 60.0),
            new Dish(dishId++, "Салат Шефский обычный", 65.0),
            new Dish(dishId++, "Салат Греческий", 75.0),
            new Dish(dishId++, "Красный борщ", 50.0),
            new Dish(dishId++, "Устрицы", 140.0),
            new Dish(dishId++, "Кофе американо", 25.0),
            new Dish(dishId++, "Кофе эспрессо", 30.0),
            new Dish(dishId++, "Чай черный", 20.0),
            new Dish(dishId++, "Чай зеленый", 20.0),
            new Dish(dishId++, "Пельмени", 80.0),
            new Dish(dishId++, "Вареники", 60.0),
            new Dish(dishId++, "Рагу", 75.0)
    );

   private DishTestData(){}

    public static void assertMatch(Dish actual, Dish expected) {
        //assertThat(actual).isEqualTo(expected);
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "imgFilePath");
    }

    public static ResultMatcher getDishMatcher(Dish expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Dish.class), expected);
    }

}
