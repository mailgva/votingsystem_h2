package com.voting.service;

import com.voting.TestUtil;
import com.voting.testdata.RestoTestData;
import com.voting.model.DailyMenu;
import com.voting.model.DailyMenuDish;
import com.voting.model.Resto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Set;

import static com.voting.testdata.DailyMenuTestData.TEST_DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class DailyMenuServiceTest extends AbstractServiceTest{

    @Autowired
    private DailyMenuService service;

    @Autowired
    private RestoService restoService;

    @Autowired
    private DishService dishService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("daily_menu").clear();
    }


    @Test
    @Transactional
    public void getByDate() throws ParseException {
        Set<DailyMenu> dm = service.getByDate(TEST_DATE);
        assertEquals(dm.size(), 3);
    }

    @Test
    public void create() throws ParseException {
        DailyMenu dm = new DailyMenu();

        dm.setDate(TEST_DATE);

        Resto resto = restoService.get(100005);
        dm.setResto(resto);

        DailyMenuDish dmd = new DailyMenuDish();
        dmd.setDish(dishService.get(100035));
        dm.addDailyMenuDish(dmd);

        dmd = new DailyMenuDish();
        dmd.setDish(dishService.get(100036));
        dm.addDailyMenuDish(dmd);

        service.create(dm);

        Set<DailyMenu> dmSet = service.getByDate(TEST_DATE);
        assertEquals(dmSet.size(), 4);
    }

    @Test
    @Transactional
    public void update() {
        DailyMenu dailyMenu = service.get(100040);
        dailyMenu.setResto(restoService.get(100005));
        DailyMenuDish dmd = new DailyMenuDish();
        dmd.setDish(dishService.get(100012));
        dailyMenu.addDailyMenuDish(dmd);
        service.update(dailyMenu);
        DailyMenu dmUpdated = service.get(100040);
        assertEquals(dailyMenu.getDmDishes().size(), dmUpdated.getDmDishes().size());
        //assertThat(dmUpdated).isEqualToComparingFieldByField(dailyMenu);
    }

    @Test
    public void delete() throws ParseException {
        service.delete(100039);
        Set<DailyMenu> dmSet = service.getByDate(TEST_DATE);
        assertEquals(dmSet.size(), 2);
    }

    @Test
    @Transactional
    public void get() {
        DailyMenu dailyMenu = service.get(100038);
        assertEquals(dailyMenu.getResto(), TestUtil.getById(RestoTestData.restos, 100003));
    }

}