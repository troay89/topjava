package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 11, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 1, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 11, 20, 0), "Ужин", 1500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 5, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 26, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 8, 11, 0), "Ужин", 410),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 8, 13, 0), "Обед", 1500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 26, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 26, 20, 0), "Ужин", 1500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 8, 10, 0), "Завтрак", 500)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        List<UserMealWithExcess> mealsTo2 = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo2.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        meals.sort(new Comparator<UserMeal>() {
            @Override
            public int compare(UserMeal o1, UserMeal o2) {
                return o1.getDateTime().toLocalDate().compareTo(o2.getDateTime().toLocalDate());
            }
        });
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        List<UserMeal> dayUserMeals = new ArrayList<>();
        int calories = 0;
        int count = 0;
        LocalDate date = meals.get(0).getDateTime().toLocalDate().plusDays(1);
        for(UserMeal meal : meals){
            meals.sort(new Comparator<UserMeal>() {
                @Override
                public int compare(UserMeal o1, UserMeal o2) {
                    return o1.getDateTime().compareTo(o2.getDateTime());
                }
            });
            if(meal.getDateTime().toLocalDate().isBefore(date)){
                dayUserMeals.add(meal);
                calories += meal.getCalories();
            }
            else {
                userMealWithExcesses.addAll(userMealWithExcesses(caloriesPerDay, dayUserMeals, calories, startTime, endTime));
                date = meal.getDateTime().toLocalDate().plusDays(1);
                calories = meal.getCalories();
                dayUserMeals.clear();
                dayUserMeals.add(meal);
            }
            count++;
            if(count == meals.size()){
                userMealWithExcesses.addAll(userMealWithExcesses(caloriesPerDay, dayUserMeals, calories, startTime, endTime));
            }
        }
        return userMealWithExcesses;
    }

    private static List<UserMealWithExcess> userMealWithExcesses(int caloriesPerDay, List<UserMeal> dayUserMeals, int calories, LocalTime startTime, LocalTime endTime){
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        if(calories > caloriesPerDay){
            for(UserMeal userMeal : dayUserMeals){
                if(TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)){
                    userMealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
                }
            }
        }
        else {
            for (UserMeal userMeal : dayUserMeals) {
                if(TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                    userMealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false));
                }
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        Map<LocalDate, Integer> map = meals.stream().collect(Collectors.groupingBy(x -> x.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        return meals.stream().filter(meal -> meal.getDateTime().toLocalTime().isAfter(startTime) &&
                meal.getDateTime().toLocalTime().isBefore(endTime)).map(meal -> new UserMealWithExcess(
                meal.getDateTime(), meal.getDescription(), meal.getCalories(), map.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)).collect(Collectors.toList());
    }
}