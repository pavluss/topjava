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
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
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

    @Test
    public void get() {
        //given
        Meal mealWithStartMealId = MealTestData.meal1;
        //when
        Meal getMealWithStartMealId = service.get(START_USER_MEAL_ID, USER_ID);
        //then
        assertMatch(getMealWithStartMealId, mealWithStartMealId);
    }

    @Test
    public void getNotExist() {
        //given
        //when
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXIST_MEAL_ID, USER_ID));
        //then
    }

    @Test
    public void getNotOwn() {
        //given
        Meal mealOwnUser = MealTestData.meal1;
        //when
        assertThrows(NotFoundException.class, () -> service.get(mealOwnUser.getId(), ADMIN_ID));
        //then
    }

    @Test
    public void delete() {
        //given
        Meal mealForDelete = MealTestData.meal1;
        //when
        service.delete(mealForDelete.getId(), USER_ID);
        //then
        assertThrows(NotFoundException.class, () -> service.get(mealForDelete.getId(), USER_ID));
    }

    @Test
    public void deleteNotExist() {
        //given
        //when
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXIST_MEAL_ID, USER_ID));
        //then
    }

    @Test
    public void deleteNotOwn() {
        //given
        Meal mealOwnUser = MealTestData.meal1;
        //when
        assertThrows(NotFoundException.class, () -> service.delete(mealOwnUser.getId(), ADMIN_ID));
        //then
    }

    @Test
    public void getBetweenInclusive() {
        //given
        LocalDate startDate = LocalDate.of(2020, Month.JANUARY, 30);
        LocalDate endDate = LocalDate.of(2020, Month.JANUARY, 30);
        List<Meal> mealsBetweenDate = Arrays.asList(meal3, meal2, meal1);
        //when
        List<Meal> obtainedMealsBetweenDate = service.getBetweenInclusive(startDate, endDate, USER_ID);
        //then
        assertMatch(obtainedMealsBetweenDate, mealsBetweenDate);
    }

    @Test
    public void getBetweenInclusiveWithNull() {
        //given
        List<Meal> allMeals = MealTestData.meals;
        //when
        List<Meal> obtainedMealsBetweenDate = service.getBetweenInclusive(null, null, USER_ID);
        //then
        assertMatch(obtainedMealsBetweenDate, allMeals);
    }

    @Test
    public void getAll() {
        //given
        List<Meal> allUserMeals = MealTestData.meals;
        //when
        List<Meal> obtainedAllUserMeals = service.getAll(USER_ID);
        //then
        assertMatch(obtainedAllUserMeals, allUserMeals);
    }

    @Test
    public void update() {
        //given
        Meal mealToUpdate = MealTestData.getUpdated();
        //when
        service.update(mealToUpdate, USER_ID);
        //then
        assertMatch(service.get(mealToUpdate.getId(), USER_ID), mealToUpdate);
    }

    @Test
    public void updateNotOwn() {
        //given
        Meal mealToUpdate = MealTestData.getUpdated();
        //when
        assertThrows(NotFoundException.class, () -> service.update(mealToUpdate, ADMIN_ID));
        //then
    }

    @Test
    public void create() {
        //given
        Meal mealToSave = MealTestData.getNew();
        //when
        Meal savedMeal = service.create(mealToSave, USER_ID);
        //then
        assertMatch(savedMeal, mealToSave);
        assertMatch(service.get(savedMeal.getId(), USER_ID), mealToSave);
    }

    @Test
    public void createWithDuplicateDatTime() {
        //given
        Meal existMeal = MealTestData.meal1;
        //when
        assertThrows(DuplicateKeyException.class, () ->
                service.create(new Meal(existMeal.getDateTime(), "duplicate", 100), USER_ID));
        //then
    }
}