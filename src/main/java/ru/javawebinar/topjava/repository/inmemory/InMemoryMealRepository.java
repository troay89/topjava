package ru.javawebinar.topjava.repository.inmemory;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.*;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> userMealMap = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, USER_ID));
        save(new Meal(LocalDateTime.of(2015, Month.JUNE,1,14,0), "ланч", 510),1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),1);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals = userMealMap.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = userMealMap.get(userId);
        return meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = userMealMap.get(userId);
        return meals.get(id);
    }



    @Override
    public List<Meal> getAll(int userId) {
        return getAllFiltered(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return getAllFiltered(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startDate, endDate));
    }


    private List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> meals = userMealMap.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() : meals.values().stream().filter(filter).
                sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }
}

