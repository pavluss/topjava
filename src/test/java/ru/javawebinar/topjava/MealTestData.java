package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int START_USER_MEAL_ID = START_SEQ + 3;
    public static final int ADMIN_MEAL_ID = START_SEQ + 20;
    public static final int NOT_EXIST_MEAL_ID = 100;


    public static final Meal meal1 = new Meal(START_USER_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(START_USER_MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(START_USER_MEAL_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(START_USER_MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(START_USER_MEAL_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500);
    public static final Meal meal6 = new Meal(START_USER_MEAL_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 1000);
    public static final Meal meal7 = new Meal(START_USER_MEAL_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 510);
    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 14, 0), "Админ ланч", 510);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 31, 21, 0), "Админ ужин", 1500);


    public static final List<Meal> meals = Arrays.asList(meal7, meal6, meal5, meal4, meal3, meal2, meal1);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2024, Month.JANUARY, 31, 20, 0), "new", 100);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDescription("update");
        updated.setCalories(100);
        return updated;
    }


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

}
