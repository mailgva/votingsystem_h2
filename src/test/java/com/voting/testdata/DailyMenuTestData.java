package com.voting.testdata;

import com.voting.TestUtil;
import com.voting.model.DailyMenu;
import com.voting.model.DailyMenuDish;

import java.util.*;

import static com.voting.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class DailyMenuTestData {
    private static final Date date = new GregorianCalendar(2018, Calendar.NOVEMBER,21).getTime();

    public static int dailyMenuId = START_SEQ + 2 + 5 + 30; // 2 - users, 5 - restaurants, 30 - dishes

    public static int dailyMenuDishId = START_SEQ + 2 + 5 + 30 + 9; // 2 - users, 5 - restaurants, 30 - dishes, 9 - dailyMenu

    private static final List<DailyMenuDish> dmDish1 = List.of(
            new DailyMenuDish(dailyMenuDishId++, TestUtil.getByName(DishTestData.dishes, "Салат Оливье")),
            new DailyMenuDish(dailyMenuDishId++, TestUtil.getByName(DishTestData.dishes, "Салат Столичный"))
    );

    private static final List<DailyMenuDish> dmDish2 = List.of(
            new DailyMenuDish(dailyMenuDishId++, TestUtil.getByName(DishTestData.dishes, "Салат Цезарь")),
            new DailyMenuDish(dailyMenuDishId++, TestUtil.getByName(DishTestData.dishes, "Стейк"))
    );

    private static final List<DailyMenuDish> dmDish3 = List.of(
            new DailyMenuDish(dailyMenuDishId++, TestUtil.getByName(DishTestData.dishes, "Шашлык")),
            new DailyMenuDish(dailyMenuDishId++, TestUtil.getByName(DishTestData.dishes, "Салат Шефский"))
    );
    
    public static final List<DailyMenu> dailyMenus = List.of(
            new DailyMenu(dailyMenuId++, date, TestUtil.getByName(RestoTestData.restos, "Ресторан 1"), dmDish1),
            new DailyMenu(dailyMenuId++, date, TestUtil.getByName(RestoTestData.restos, "Ресторан 2"), dmDish2),
            new DailyMenu(dailyMenuId++, date, TestUtil.getByName(RestoTestData.restos, "Ресторан 3"), dmDish3)
    );
    

    private DailyMenuTestData() {
    }

    public static void assertMatch(DailyMenu actual, DailyMenu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected,  "dishes");
    }

}
