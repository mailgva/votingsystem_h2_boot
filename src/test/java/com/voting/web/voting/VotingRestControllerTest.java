package com.voting.web.voting;

import com.voting.TestUtil;
import com.voting.model.Resto;
import com.voting.testdata.RestoTestData;
import com.voting.web.AbstractControllerTest;
import com.voting.web.dish.DishRestController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.voting.TestUtil.userHttpBasic;
import static com.voting.testdata.UserTestData.USER;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VotingRestControllerTest  extends AbstractControllerTest {
    private static final String REST_URL = VotingRestController.REST_URL + '/';

    @Test
    void testSetUserVote() throws Exception {
        Resto resto = TestUtil.getByName(RestoTestData.restos, "Ресторан 2");
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType("application/x-www-form-urlencoded")
                .param("date", "2019-01-20")
                .param("restoId", String.valueOf(resto.getId()))
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testSetUserVoteInvalid() throws Exception {
        Resto resto = TestUtil.getByName(RestoTestData.restos, "Ресторан 2");
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType("application/x-www-form-urlencoded")
                .param("date", "2018-01-20")
                .param("restoId", String.valueOf(resto.getId()))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }
}