package com.voting.repository;

import com.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    Vote save(Vote vote, int userId);

    //boolean delete(int id, int userId);
    boolean delete(int id);

    // null if meal do not belong to userId
    Vote get(int id, int userId);

    // ORDERED dateTime desc
    List<Vote> getAll();

    Vote getByDate(LocalDate date, int userId);

    List<Vote> getByDateUsers(LocalDate date);


}
