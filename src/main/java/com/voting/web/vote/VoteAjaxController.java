package com.voting.web.vote;

import com.voting.model.Vote;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ajax/admin/votes")
public class VoteAjaxController extends AbstractVoteController {
    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/bydate/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getByDateUsers(@RequestParam(value = "date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        return super.getByDateUsers(date);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote get(@PathVariable("id") Vote vote) {
        return vote;
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@Valid Vote vote) {
        if (vote.isNew()) {
            super.create(vote);
        } else {
            super.update(vote, vote.getId());
        }
    }
}
