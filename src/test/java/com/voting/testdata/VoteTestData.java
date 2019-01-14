package com.voting.testdata;

import com.voting.TestUtil;
import com.voting.model.User;
import com.voting.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static com.voting.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    private VoteTestData(){}

    private static LocalDate ld = LocalDate.of(2018, Month.NOVEMBER, 21);

    private final static Date date1 = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    private final static Date date2 = Date.from(ld.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    private final static Date date3 = Date.from(ld.plusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant());


    public static int voteId = START_SEQ + 2 + 5 + 30 +9 + 18; // 2 - users, 5 - restaurants, 30 - dishes, 9 - dailymenu, 18 - dailymenudetail

    public static List<Vote> votes = List.of(
            new Vote(voteId++, UserTestData.USER,  TestUtil.getByName(RestoTestData.restos, "Ресторан 1"), date1, LocalDateTime.now()),
            new Vote(voteId++, UserTestData.ADMIN, TestUtil.getByName(RestoTestData.restos, "Ресторан 2"), date1, LocalDateTime.now()),
            new Vote(voteId++, UserTestData.USER,  TestUtil.getByName(RestoTestData.restos, "Ресторан 1"), date2, LocalDateTime.now()),
            new Vote(voteId++, UserTestData.ADMIN, TestUtil.getByName(RestoTestData.restos, "Ресторан 3"), date2, LocalDateTime.now()),
            new Vote(voteId++, UserTestData.USER,  TestUtil.getByName(RestoTestData.restos, "Ресторан 2"), date3, LocalDateTime.now()),
            new Vote(voteId++, UserTestData.ADMIN, TestUtil.getByName(RestoTestData.restos, "Ресторан 2"), date3, LocalDateTime.now())
    );

    public static Vote getByDateUser(Date date, User user) {
        return votes.stream()
                .filter(v -> (v.getUser().equals(user) && v.getDate().equals(date)))
                .findFirst()
                .orElse(null);
    }



}
