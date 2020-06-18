package ru.javawebinar.topjava.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.util.DateTimeUtil.*;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository){
        this.repository = repository;
    }

    public Meal get(int id, int userId){
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public void delete(int id, int userId){
        checkNotFoundWithId(repository.delete(id, userId),id);
    }

    public List<Meal> getBetweenHalfOpen(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId){
        return repository.getBetweenHalfOpen(getStartInclusive(startDate), getEndExclusive(endDate), userId);
    }

    public List<Meal> getAll(int userId){
        return repository.getAll(userId);
    }

    public void update(Meal meal, int userId){
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }
}