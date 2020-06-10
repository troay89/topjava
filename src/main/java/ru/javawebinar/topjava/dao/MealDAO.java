package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DbUtil;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class MealDAO implements MealRepository {

    private final Connection connection;

    public MealDAO() {
        connection = DbUtil.getConnection();
    }


    @Override
    public void addMeal(Meal meal) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users(datetime, description, calories) values (?, ?, ?)");
            preparedStatement.setTimestamp(1, meal.getDateSQL());
            preparedStatement.setString(2, meal.getDescription());
            preparedStatement.setInt(3, meal.getCalories());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteMeal(int mealId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from users where id=?");
            preparedStatement.setInt(1, mealId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Meal> getAllMeal() {
        List<Meal> meals = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                Meal meal = new Meal();
                meal.setId(rs.getInt("id"));
                meal.setDateTime(rs.getTimestamp("datetime").toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDateTime());
                meal.setDescription(rs.getString("description"));
                meal.setCalories(rs.getInt("calories"));
                meals.add(meal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meals;
    }

    @Override
    public void updateMeal(Meal meal) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set datetime=?, description=?, calories=? where id=?");
            preparedStatement.setTimestamp(1, meal.getDateSQL());
            preparedStatement.setString(2, meal.getDescription());
            preparedStatement.setInt(3, meal.getCalories());
            preparedStatement.setInt(4, meal.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Meal getUserById(int mealId) {
        Meal meal = new Meal();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id=?");
            preparedStatement.setInt(1, mealId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                meal.setId(rs.getInt("id"));
                meal.setDateTime(rs.getTimestamp("datetime").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                meal.setDescription(rs.getString("description"));
                meal.setCalories(rs.getInt("calories"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meal;
    }
}
