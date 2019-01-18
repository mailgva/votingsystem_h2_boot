package com.voting.web.dish;

import com.voting.TestUtil;
import com.voting.model.Dish;
import com.voting.testdata.DishTestData;
import com.voting.web.AbstractControllerTest;
import com.voting.web.dailymenu.DailyMenuRestController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.voting.TestUtil.userHttpBasic;
import static com.voting.testdata.DishTestData.getDishMatcher;
import static com.voting.testdata.UserTestData.*;
import static com.voting.testdata.UserTestData.ADMIN;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = DishRestController.REST_URL + '/';
    private static final Dish DISH = TestUtil.getByName(DishTestData.dishes, "Салат Цезарь");

    @Test
    void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + DISH.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getDishMatcher(DISH));
    }

    @Test
    void testGetForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + DISH.getId())
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


}