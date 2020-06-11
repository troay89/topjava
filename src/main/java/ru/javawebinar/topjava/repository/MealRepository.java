package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {

    Meal saveMeal(Meal meal);

    boolean deleteMeal(int mealId);

    Collection<Meal> getAllMeal();

    Meal getUserById(int userId);
}
