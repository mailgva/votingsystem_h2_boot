package com.voting.testdata;

import com.voting.TestUtil;
import com.voting.model.User;
import com.voting.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.voting.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    private VoteTestData(){}

    public static final LocalDate TEST_DATE = LocalDate.of(2018,11,21);

    private final static LocalDate date1 = TEST_DATE;
    private final static LocalDate date2 = TEST_DATE.plusDays(1);
    private final static LocalDate date3 = TEST_DATE.plusDays(2);


    public static int voteId = START_SEQ + 2 + 5 + 30 +9 + 18; // 2 - users, 5 - restaurants, 30 - dishes, 9 - dailymenu, 18 - dailymenudetail

    public static List<Vote> votes = List.of(
            new Vote(voteId++, UserTestData.USER,  TestUtil.getByName(RestoTestData.restos, "Ресторан 1"), date1, LocalDateTime.now()),
            new Vote(voteId++, UserTestData.ADMIN, TestUtil.getByName(RestoTestData.restos, "Ресторан 2"), date1, LocalDateTime.now()),
            new Vote(voteId++, UserTestData.USER,  TestUtil.getByName(RestoTestData.restos, "Ресторан 1"), date2, LocalDateTime.now()),
            new Vote(voteId++, UserTestData.ADMIN, TestUtil.getByName(RestoTestData.restos, "Ресторан 3"), date2, LocalDateTime.now()),
            new Vote(voteId++, UserTestData.USER,  TestUtil.getByName(RestoTestData.restos, "Ресторан 2"), date3, LocalDateTime.now()),
            new Vote(voteId++, UserTestData.ADMIN, TestUtil.getByName(RestoTestData.restos, "Ресторан 2"), date3, LocalDateTime.now())
    );

    public static Vote getByDateUser(LocalDate date, User user) {
        return votes.stream()
                .filter(v -> (v.getUser().equals(user) && v.getDate().equals(date)))
                .findFirst()
                .orElse(null);
    }



}
