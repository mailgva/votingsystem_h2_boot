package com.voting.web.voting;

import com.voting.TestUtil;
import com.voting.model.Resto;
import com.voting.testdata.RestoTestData;
import com.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.voting.TestUtil.userHttpBasic;
import static com.voting.testdata.UserTestData.USER;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VotingRestControllerTest  extends AbstractControllerTest {
    private static final String REST_URL = VotingRestController.REST_URL + '/';

    private static final LocalDate DATE_AFTER = LocalDate.of(2050, 1, 1);
    private static final LocalDate DATE_BEFORE = LocalDate.of(2015, 1, 1);
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Test
    void testSetUserVote() throws Exception {

        Resto resto = TestUtil.getByName(RestoTestData.restos, "Ресторан 2");
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType("application/x-www-form-urlencoded")
                .param("date", DATE_AFTER.format(DATE_TIME_FORMAT))
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
                .param("date", DATE_BEFORE.format(DATE_TIME_FORMAT))
                .param("restoId", String.valueOf(resto.getId()))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }
}