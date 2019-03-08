package com.voting.service.impl;

import com.voting.model.Vote;
import com.voting.repository.VoteRepository;
import com.voting.service.VoteService;
import com.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.voting.util.ValidationUtil.checkNotFoundWithId;
import static com.voting.util.ValidationUtil.checkTooLate;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository repository;

    @Autowired
    public VoteServiceImpl(VoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Vote get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @CacheEvict(value = "votes", allEntries = true)
    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Cacheable("votes")
    @Override
    public List<Vote> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "votes", allEntries = true)
    @Override
    public void update(Vote vote, int userId) throws NotFoundException {
        checkTooLate(vote);
        repository.save(vote, userId);
    }

    @CacheEvict(value = "votes", allEntries = true)
    @Override
    public Vote create(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        checkTooLate(vote);
        return repository.save(vote, userId);
    }

    @Override
    public Vote getByDate(LocalDate date, int userId) {
        return repository.getByDate(date, userId);
    }

    @Override
    public List<Vote> getByDateUsers(LocalDate date) {
        return repository.getByDateUsers(date);
    }
}
