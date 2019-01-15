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
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public abstract class AbstractVotingController {

    @Autowired
    private VoteService voteService;

    @Autowired
    private DailyMenuService dailyMenuService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestoService restoService;

    public List<DailyMenuTo> getDailyMenu(Date date) {
        int userId = SecurityUtil.authUserId();
        Vote vote = voteService.getByDate(date, userId);
        return getDailyMenuTo(date, vote);
    }

    protected List<DailyMenuTo> getDailyMenuTo(Date date, Vote vote) {
        return DailyMenuUtil.convertToDailyMenuTo(date, dailyMenuService.getByDate(date), vote);
    }

    public void setUserVote(Date date, int id) {
        setUserVote(date, restoService.get(id));
    }

    public void setUserVote(Date date, Resto resto) {
        int userId = SecurityUtil.authUserId();
        Vote vote   = voteService.getByDate(date, userId);
        User user   = userService.get(userId);

        if(vote == null)
            vote = new Vote(user, resto, date, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        if (vote.getId() == null) {
            voteService.create(vote, userId);
        } else {
            voteService.update(vote, userId);
        }
    }


}
