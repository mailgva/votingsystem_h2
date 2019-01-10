package com.voting.service.impl;

import com.voting.model.Vote;
import com.voting.repository.VoteRepository;
import com.voting.service.VoteService;
import com.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
    @Transactional
    public Vote get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public List<Vote> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(endDateTime, "endDateTime  must not be null");
        return repository.getBetween(startDateTime, endDateTime, userId);
    }

    @Override
    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public void update(Vote vote, int userId) throws NotFoundException {
        checkTooLate(vote);
        repository.save(vote, userId);
    }

    @Override
    public Vote create(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        checkTooLate(vote);
        return repository.save(vote, userId);
    }

    @Override
    public List<Vote> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId) {
        return null; // repository.getBetween(startDate,endDate,userId);
    }

    @Override
    public Vote getByDate(Date date, int userId) {
        return repository.getByDate(date, userId);
    }
}
