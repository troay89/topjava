package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/users.jsp";
    private static String LIST_USER = "/meals.jsp";

    private final MealRepository dao;

    public MealServlet() {
        super();
        dao = new MealDAO();
    }

    private List<Meal> foodUse = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        log.debug("redirect to meals");
//        List<MealTo> wentThroughCalories = MealsUtil.filteredByStreams(foodUse, LocalTime.MIN, LocalTime.MAX, 2000);
//        req.setAttribute("mealList", wentThroughCalories);
//        getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);
//    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("delete")) {
            dao.deleteMeal(Integer.parseInt(request.getParameter("mealId")));
            forward = LIST_USER;
            request.setAttribute("mealList", MealsUtil.filteredByStreams(dao.getAllMeal(), LocalTime.MIN, LocalTime.MAX, 2000));
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            Meal meal = dao.getUserById(Integer.parseInt(request.getParameter("mealId")));
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listUser")) {
            log.debug("creat list");
            forward = LIST_USER;
            request.setAttribute("mealList", MealsUtil.filteredByStreams(dao.getAllMeal(), LocalTime.MIN, LocalTime.MAX, 2000));
        } else {
            forward = INSERT_OR_EDIT;
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Meal meal = new Meal();
        try {
            String time = request.getParameter("datatime");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
            meal.setDateTime(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        meal.setDescription(request.getParameter("description1"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        String mealId = request.getParameter("id");
        if (mealId == null || mealId.isEmpty()) {
            dao.addMeal(meal);
        } else {
            meal.setId(Integer.parseInt(mealId));
            dao.updateMeal(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
        request.setAttribute("mealList", MealsUtil.filteredByStreams(dao.getAllMeal(), LocalTime.MIN, LocalTime.MAX, 2000));
        view.forward(request, response);
    }
}
