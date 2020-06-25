package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;

    @Test
    public void create() throws Exception {
        Meal newMeal = getNewMeal();
        Meal created = service.create(newMeal, ADMIN_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertEquals(created, newMeal);
        assertEquals(service.get(newId, ADMIN_ID), newMeal);
    }

    @Test
    public void delete() throws Exception {
        service.delete(100009, ADMIN_ID);
        assertNull(repository.get(100009, ADMIN_ID));
    }

    @Test
    public void alienFoodDelete() {
        assertThrows(NotFoundException.class, () -> service.delete(100009, USER_ID));
    }


    @Test
    public void get() throws Exception {
        Meal meal = service.get(100005, USER_ID);
        System.out.println(meal);
        assertThat(meal).isEqualToIgnoringGivenFields(meal4, "id");
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(1000, USER_ID));
    }

    @Test
    public void alienId() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(100005, ADMIN_ID));
    }

    @Test
    public void update() throws Exception {
        Meal updated = getNewMeal();
        service.update(updated, USER_ID);
        assertEquals(service.get(100011, USER_ID), updated);
    }

    @Test
    public void alienUpdate() throws Exception {
        Meal updated = getNewMeal();
        service.update(updated, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(100011, ADMIN_ID));
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(USER_ID);
        assertEquals(all.size(), 7);
    }

    @Test
    public void getBetweenHalfOpen() throws Exception{
        List<Meal> filterTimeMeal = service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30), USER_ID);
        assertEquals(filterTimeMeal.size(), 3);
    }

    @Test
    public void sameDateTime() throws Exception{
        assertThrows(DuplicateKeyException.class, () -> service.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 29, 10, 0),
                "Test", 2000), ADMIN_ID));
    }

    @Test
    public void getAll2() throws Exception{
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, meal7, meal6, meal5, meal4, meal3, meal2, meal1);
    }

}
