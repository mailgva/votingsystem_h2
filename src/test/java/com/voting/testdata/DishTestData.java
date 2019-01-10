package com.voting.testdata;

import com.voting.model.Dish;

import java.util.List;

import static com.voting.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static int dishId = START_SEQ + 2 + 5; // 2 - users, 5 - restaurants

    public static List<Dish> dishes = List.of(
            new Dish(dishId++, "Салат Оливье", 50.0),
            new Dish(dishId++, "Салат Столичный", 40.0),
            new Dish(dishId++, "Салат Цезарь", 90.0),
            new Dish(dishId++, "Стейк", 150.0),
            new Dish(dishId++, "Шашлык", 120.0),
            new Dish(dishId++, "Салат Шефский", 70.0),
            new Dish(dishId++, "Салат Греческий особенный", 80.0),
            new Dish(dishId++, "Стейк от шефа", 140.0),
            new Dish(dishId++, "Шашлык шея", 130.0),
            new Dish(dishId++, "Запеченные овощи", 110.0),
            new Dish(dishId++, "Пицца", 125.0),
            new Dish(dishId++, "Картошка Фри", 40.0),
            new Dish(dishId++, "Картошка запеченная", 45.0),
            new Dish(dishId++, "Стейк с картошкой", 170.0),
            new Dish(dishId++, "Шашлык телятина", 135.0),
            new Dish(dishId++, "Запеченные овощи в тандыре", 70.0),
            new Dish(dishId++, "Курица Гриль", 105.0),
            new Dish(dishId++, "Сэндвич", 50.0),
            new Dish(dishId++, "Суп Харчо", 60.0),
            new Dish(dishId++, "Салат Шефский обычный", 65.0),
            new Dish(dishId++, "Салат Греческий", 75.0),
            new Dish(dishId++, "Красный борщ", 50.0),
            new Dish(dishId++, "Устрицы", 140.0),
            new Dish(dishId++, "Кофе американо", 25.0),
            new Dish(dishId++, "Кофе эспрессо", 30.0),
            new Dish(dishId++, "Чай черный", 20.0),
            new Dish(dishId++, "Чай зеленый", 20.0),
            new Dish(dishId++, "Пельмени", 80.0),
            new Dish(dishId++, "Вареники", 60.0),
            new Dish(dishId++, "Рагу", 75.0)
    );

   /* public static void main(String[] args) {
        System.out.println(TestUtil.getById(dishes, 100017));
        System.out.println(TestUtil.getByName(dishes, "Салат Греческий"));

    }*/

}
