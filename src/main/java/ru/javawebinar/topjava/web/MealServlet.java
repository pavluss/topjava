package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private MealRepository repository;

    @Override
    public void init() throws ServletException {
        repository = new MemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            log.info("get all");
            request.setAttribute("mealToList", MealsUtil.getAllMeatTo(repository.getAll(), MealsUtil.CALORIES_PER_DAY));
            request.getRequestDispatcher("WEB-INF/jsp/meals.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case "delete":
                int id = Integer.parseInt(request.getParameter("id"));
                log.info("Delete id={}", id);
                repository.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        repository.get(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.getId() == null ? "Create {}" : "Update {}", meal);
        repository.save(meal);
        response.sendRedirect("meals");
    }
}
