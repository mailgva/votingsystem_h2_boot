package com.voting.web.voting;

import com.voting.model.Resto;
import com.voting.model.User;
import com.voting.model.Vote;
import com.voting.service.DailyMenuService;
import com.voting.service.RestoService;
import com.voting.service.UserService;
import com.voting.service.VoteService;
import com.voting.to.DailyMenuTo;
import com.voting.util.DailyMenuUtil;
import com.voting.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public abstract class AbstractVotingController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService voteService;

    @Autowired
    private DailyMenuService dailyMenuService;

    @Autowired
    private UserService userService;

    public List<DailyMenuTo> getDailyMenu(LocalDate date) {
        log.info("getDailyMenu {}", date);
        int userId = SecurityUtil.authUserId();
        Vote vote = voteService.getByDate(date, userId);
        return getDailyMenuTo(date, vote);
    }

    protected List<DailyMenuTo> getDailyMenuTo(LocalDate date, Vote vote) {
        log.info("getDailyMenuTo Date={}; Vote={}", date, vote);
        return DailyMenuUtil.convertToDailyMenuTo(date, dailyMenuService.getByDate(date), vote);
    }


    public void setUserVote(LocalDate date, Resto resto) {
        log.info("setUserVote Date={}; Resto={}", date, resto);
        int userId = SecurityUtil.authUserId();
        Vote vote  = voteService.getByDate(date, userId);

        if(vote == null) {
            User user   = userService.get(userId);
            vote = new Vote(user, resto, date, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        } else {
            vote.setResto(resto);
            vote.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        }


        if (vote.getId() == null) {
            voteService.create(vote, userId);
        } else {
            voteService.update(vote, userId);
        }
    }


}
