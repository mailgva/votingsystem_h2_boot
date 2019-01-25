package com.voting.web.voting;

import com.voting.model.Resto;
import com.voting.to.DailyMenuTo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = VotingAjaxController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VotingAjaxController extends AbstractVotingController {
    static final String REST_URL = "/ajax/voting";

    @Override
    @GetMapping
    public List<DailyMenuTo> getDailyMenu(@RequestParam(value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        return super.getDailyMenu(date);
    }

    @PostMapping
    public void setVote(@RequestParam(value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                                          @RequestParam(value = "restoId") Resto resto ) {
        super.setUserVote(date, resto);
    }


}
