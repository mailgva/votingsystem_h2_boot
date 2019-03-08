package com.voting.repository.impl;

import com.voting.model.Vote;
import com.voting.repository.VoteRepository;
import com.voting.repository.impl.crud.CrudVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class VoteRepositoryImpl implements VoteRepository {

    @Autowired
    private CrudVoteRepository crudVoteRepository;

    @Override
    //@Transactional
    public Vote save(Vote vote, int userId) {
        /*if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }*/
        return crudVoteRepository.save(vote);

    }

    @Override
    //@Transactional
    public boolean delete(int id, int userId) {
        return crudVoteRepository.delete(id, userId) != 0;
    }

    @Override
    public Vote get(int id, int userId) {
        return crudVoteRepository.get(id);
    }

    @Override
    public List<Vote> getAll() {
        return crudVoteRepository.getAll();
    }

    @Override
    public Vote getByDate(LocalDate date, int userId) {
        return crudVoteRepository.getByDate(date, userId);
    }

    @Override
    public List<Vote> getByDateUsers(LocalDate date) {
        return crudVoteRepository.getAllByDate(date);
    }

}
