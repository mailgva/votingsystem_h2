package com.voting.service;

import com.voting.TestUtil;
import com.voting.model.Resto;
import com.voting.model.User;
import com.voting.model.Vote;
import com.voting.testdata.RestoTestData;
import com.voting.util.exception.PastDateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataIntegrityViolationException;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.voting.testdata.DailyMenuTestData.TEST_DATE;

import static com.voting.testdata.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class VoteServiceTest extends AbstractServiceTest{
    private final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    private VoteService service;


    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("votes").clear();
    }

    @Test
    public void create() throws ParseException {
        User user = ADMIN;
        Resto resto = TestUtil.getByName(RestoTestData.restos, "Ресторан 2");
        LocalDate ld = LocalDate.now().plusDays(1);

        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Vote vote = new Vote(user, resto, date, LocalDateTime.now());
        service.create(vote, user.getId());
    }

    @Test
    public void update() {
        assertThrows(PastDateException.class, () -> {
            Date testDate_02_11_2018 = new GregorianCalendar(2018, Calendar.NOVEMBER,2).getTime();
            Date testDate_01_11_2018 = new GregorianCalendar(2018, Calendar.NOVEMBER,1).getTime();

            User user = ADMIN;
            Resto resto = TestUtil.getByName(RestoTestData.restos, "Ресторан 2");

            Vote vote = new Vote(user, resto, testDate_02_11_2018, LocalDateTime.now());
            service.create(vote, ADMIN_ID);

            Vote newVote = service.getByDate(testDate_02_11_2018, user.getId());
            newVote.setResto(TestUtil.getByName(RestoTestData.restos, "Ресторан 3"));
            newVote.setDate(testDate_01_11_2018);
            service.update(newVote, user.getId());
        });
    }

    @Test
    public void get() {
        service.get(100065, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(100065, ADMIN_ID);
    }

    @Test
    public void getAll() {
        assertEquals(service.getAll().size(), 6);
    }


    @Test
    public void getByDate() throws ParseException {
        Vote vote = service.getByDate(TEST_DATE, USER_ID);
        Resto resto = vote.getResto();
        assertEquals(resto, TestUtil.getByName(RestoTestData.restos, "Ресторан 1"));
    }

    @Test //(expected = Exception.class)
    public void createDublicat() {
        assertThrows(DataIntegrityViolationException.class, () -> {

            User user = ADMIN;
            Resto resto = TestUtil.getByName(RestoTestData.restos, "Ресторан 2");

            LocalDate ld = LocalDate.now().plusDays(10);

            Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Vote vote = new Vote(user, resto, date, LocalDateTime.now());
            Vote newVote = service.create(vote, ADMIN_ID);

            newVote.setResto(TestUtil.getByName(RestoTestData.restos, "Ресторан 3"));
            newVote.setId(null);

            service.update(newVote, ADMIN_ID);
        });
    }

}