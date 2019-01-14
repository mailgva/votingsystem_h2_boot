package com.voting.repository;

import com.voting.model.Vote;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface VoteRepository {
    // null if updated meal do not belong to userId
    Vote save(Vote vote, int userId);

    // false if meal do not belong to userId
    boolean delete(int id, int userId);

    // null if meal do not belong to userId
    Vote get(int id, int userId);

    // ORDERED dateTime desc
    List<Vote> getAll();

    // ORDERED dateTime desc
    List<Vote> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);

    Vote getByDate(Date date, int userId);

    List<Vote> getByDateUsers(Date date);


}
