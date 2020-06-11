package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDAO implements MealRepository {

    Map<Integer, Meal> repository = new ConcurrentHashMap();
    AtomicInteger idCounter = new AtomicInteger(0);

    {
        MealsUtil.mealList.forEach(this::saveMeal);
    }

    @Override
    public Meal saveMeal(Meal meal) {
        System.out.println(meal);
        if (meal.getId() == null) {
            meal.setId(idCounter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean deleteMeal(int mealId) {
        return repository.remove(mealId) != null;
    }

    @Override
    public Collection<Meal> getAllMeal() {
        return repository.values();
    }

    @Override
    public Meal getUserById(int mealId) {
        return repository.get(mealId);
    }
}
