package com.voting.service;

import com.voting.testdata.DishTestData;
import com.voting.model.Dish;
import com.voting.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class DishServiceTest extends AbstractServiceTest{

    @Autowired
    private DishService service;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("dishes").clear();
    }

    @Test
    public void create() {
        service.create(new Dish(null,"Пельмени с соусом", 55.0d));
        Dish dish = service.getByNameAndPrice("Пельмени с соусом", 55);
        assertNotNull(dish);
    }

    @Test
    void duplicateDishCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new Dish(null,"Пельмени", 50.0d)));
    }

    @Test //(expected = NotFoundException.class)
    public void deleteWithNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(100004));
    }

    @Test
    public void delete() {
        service.delete(100025);
    }


    @Test
    public void get() {
        Dish dish = service.get(100026);
        Dish expected = DishTestData.dishes.stream().filter(dish1 -> dish1.getId() == 100026).findFirst().get();
        assertThat(expected).isEqualToIgnoringGivenFields(dish, "imgFilePath");
        //assertEquals(expected, dish);
    }

    @Test
    public void getByName() {
        assertEquals(service.getByName("шеф").size(), 3);
    }

    @Test
    @Transactional
    public void update() {
        Dish dish = service.get(100025);
        dish.setPrice(99.99);
        dish.setName("Царский");
        service.update(dish);
        assertThat(service.get(100025)).isEqualToComparingFieldByField(dish);
    }

    @Test
    public void getAll() {
        assertEquals(service.getAll().size(), 30);
    }

    @Test
    public void findByNameAndPrice() {
        Dish dish = service.getByNameAndPrice("Пицца", 125);
        Dish expected = DishTestData.dishes.stream().filter(dish1 -> dish1.getId() == 100017).findFirst().get();
        assertThat(expected).isEqualToIgnoringGivenFields(dish, "imgFilePath");
        //assertEquals(expected, dish);
    }
}