package com.voting.web.vote;

import com.voting.model.Vote;
import com.voting.service.UserService;
import com.voting.service.VoteService;
import com.voting.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

import static com.voting.util.ValidationUtil.assureIdConsistent;

@Controller
public class AbstractVoteController {
    private static final Logger log = LoggerFactory.getLogger(AbstractVoteController.class);

    @Autowired
    private VoteService service;

    @Autowired
    private UserService userService;

    public Vote get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public List<Vote> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}", userId);
        return service.getAll();
    }

    public Vote getByDate(LocalDate date) {
        int userId = SecurityUtil.authUserId();
        log.info("getByDate vote {} for user {}", date, userId);
        return service.getByDate(date, userId);
    }


    public List<Vote> getByDateUsers(LocalDate date) {
        int userId = SecurityUtil.authUserId();
        log.info("getByDate vote {} for all users", date);
        return service.getByDateUsers(date);
    }


    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete vote {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public Vote create(Vote vote) {
        int userId = SecurityUtil.authUserId();
        vote.setUser(userService.get(userId));
        log.info("create {} for user {}", vote, userId);
        return service.create(vote, userId);
    }

    public void update(Vote vote, int id) {
        int userId = SecurityUtil.authUserId();
        vote.setUser(userService.get(userId));
        assureIdConsistent(vote, id);
        log.info("update {} for user {}", vote, userId);
        service.update(vote, userId);
    }


}
