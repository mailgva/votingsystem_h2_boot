package com.voting.web.dailyMenu;

import com.voting.TestUtil;
import com.voting.model.DailyMenu;
import com.voting.testdata.DailyMenuTestData;
import com.voting.web.AbstractControllerTest;
import com.voting.web.json.JsonUtil;
import com.voting.web.vote.AbstractVoteController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.voting.TestUtil.userHttpBasic;
import static com.voting.testdata.DailyMenuTestData.DAILY_MENUS;
import static com.voting.testdata.DailyMenuTestData.getDailyMenuMatcher;
import static com.voting.testdata.UserTestData.ADMIN;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DailyMenuRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = DailyMenuRestController.REST_URL + "/";
    private static final DailyMenu DAILY_MENU = TestUtil.getById(DAILY_MENUS, 100037);

    @Test
    void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + DAILY_MENU.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getDailyMenuMatcher(DAILY_MENU));
    }

    @Test
    void testGetByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/bydate/?date=2018-11-21")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getDailyMenuMatcher(DAILY_MENUS.toArray(new DailyMenu[3])));
    }

}