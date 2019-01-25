package com.voting.service;

import com.voting.model.Vote;
import com.voting.util.exception.NotFoundException;
import com.voting.util.exception.TooLateEcxeption;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {
    Vote get(int id, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;


    List<Vote> getAll();

    List<Vote> getByDateUsers(LocalDate date);

    void update(Vote vote, int userId) throws NotFoundException, TooLateEcxeption;

    Vote create(Vote vote, int userId) throws TooLateEcxeption;

    Vote getByDate(LocalDate date, int userId);
}
