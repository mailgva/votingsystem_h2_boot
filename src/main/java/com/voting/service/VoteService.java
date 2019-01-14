package com.voting.service;

import com.voting.model.Vote;
import com.voting.util.exception.NotFoundException;
import com.voting.util.exception.TooLateEcxeption;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface VoteService {
    Vote get(int id, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    default List<Vote> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId) {
        return getBetweenDateTimes(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX), userId);
    }

    List<Vote> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

    List<Vote> getAll();

    List<Vote> getByDateUsers(Date date);

    void update(Vote vote, int userId) throws NotFoundException, TooLateEcxeption;

    Vote create(Vote vote, int userId) throws TooLateEcxeption;

    Vote getByDate(Date date, int userId);
}
