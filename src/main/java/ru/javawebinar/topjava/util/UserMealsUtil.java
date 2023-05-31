package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsToByCycle = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToByCycle.forEach(System.out::println);

        List<UserMealWithExcess> mealsToByStream = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToByStream.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> symCaloriesPerDay = getSymCaloriesPerDayByCycle(meals);
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();

        for (UserMeal meal : meals) {
            LocalDateTime mealDate = meal.getDateTime();
            boolean isExcess = isExcess(symCaloriesPerDay, mealDate, caloriesPerDay);
            if (isBetweenHalfOpen(mealDate.toLocalTime(), startTime, endTime)) {
                userMealWithExcesses.add(
                    new UserMealWithExcess(mealDate, meal.getDescription(), meal.getCalories(), isExcess));
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> symCaloriesPerDay = getSumCaloriesPerDayByStream(meals);

        return meals.stream()
            .filter(meal -> isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
            .map(meal -> {
                LocalDateTime mealDate = meal.getDateTime();
                boolean isExcess = isExcess(symCaloriesPerDay, mealDate, caloriesPerDay);
                return new UserMealWithExcess(mealDate, meal.getDescription(), meal.getCalories(), isExcess);
            })
            .collect(Collectors.toList());
    }

    private static Map<LocalDate, Integer> getSymCaloriesPerDayByCycle(List<UserMeal> meals) {
        Map<LocalDate, Integer> calories = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            int caloriesCount = calories.getOrDefault(mealDate, 0) + meal.getCalories();
            calories.put(mealDate, caloriesCount);
        }
        return calories;
    }

    private static Map<LocalDate, Integer> getSumCaloriesPerDayByStream(List<UserMeal> meals) {
        return meals.stream()
            .collect(Collectors.toMap(k -> k.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));
    }

    private static boolean isExcess(Map<LocalDate, Integer> symCaloriesPerDay, LocalDateTime mealDate, int caloriesPerDay) {
        return symCaloriesPerDay.get(mealDate.toLocalDate()) > caloriesPerDay;
    }
}
