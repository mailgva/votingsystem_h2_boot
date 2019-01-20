package com.voting.web.json;

import com.voting.TestUtil;
import com.voting.model.Dish;
import com.voting.model.User;
import com.voting.testdata.DishTestData;
import com.voting.testdata.UserTestData;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.voting.testdata.DishTestData.assertMatch;
import static com.voting.testdata.UserTestData.ADMIN;
import static com.voting.testdata.UserTestData.USER;
import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {
    @Test
    void testReadWriteValue() throws Exception {
        Dish testDish = TestUtil.getByName(DishTestData.dishes, "Салат Оливье");
        String json = JsonUtil.writeValue(testDish);
        System.out.println(json);
        Dish dish = JsonUtil.readValue(json, Dish.class);
        assertMatch(dish, testDish);
    }

    @Test
    void testReadWriteValues() throws Exception {
        List<User> usersTest = List.of(USER, ADMIN);
        String json = JsonUtil.writeValue(usersTest);
        System.out.println(json);
        List<User> users = JsonUtil.readValues(json, User.class);
        UserTestData.assertMatch(users, usersTest);
    }

}