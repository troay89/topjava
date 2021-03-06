package ru.javawebinar.topjava.service.datajpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(DATAJPA)
class DataJpaMealServiceTest extends AbstractMealServiceTest {
    @Test
    void getWithUser() throws Exception {
        Meal adminMeal = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(adminMeal, ADMIN_MEAL1);
        UserTestData.USER_MATCHER.assertMatch(adminMeal.getUser(), UserTestData.ADMIN);
    }

    @Test
    void getWithUserNotFound() throws Exception {
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getWithUser(1, ADMIN_ID));
    }
}
