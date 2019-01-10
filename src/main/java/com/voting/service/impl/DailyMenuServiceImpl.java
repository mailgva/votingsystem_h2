package com.voting.service.impl;

import com.voting.model.DailyMenu;
import com.voting.repository.DailyMenuRepository;
import com.voting.service.DailyMenuService;
import com.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DailyMenuServiceImpl implements DailyMenuService {

    private final DailyMenuRepository repository;

    @Autowired
    public DailyMenuServiceImpl(DailyMenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public DailyMenu create(DailyMenu dailyMenu) {
        Assert.notNull(dailyMenu, "dailyMenu must not be null");
        return repository.save(dailyMenu);
    }

    @Override
    public void update(DailyMenu dailyMenu) {
        Assert.notNull(dailyMenu, "dailyMenu must not be null");
        checkNotFoundWithId(repository.save(dailyMenu), dailyMenu.getId());
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public DailyMenu get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public Set<DailyMenu> getByDate(Date date) {
        return new HashSet<>(repository.getByDate(date));
    }

    @Override
    public List<DailyMenu> getByNameResto(String nameResto) {
        return repository.getByNameResto(nameResto);
    }

    @Override
    public List<DailyMenu> getAll() {
        return repository.getAll();
    }

    @Override
    public void generateDailyMenu(Date date) {
        repository.deleteByDate(date);
        repository.generateDailyMenu(date);
    }
}
