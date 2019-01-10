package com.voting.service;

import com.voting.TestUtil;
import com.voting.model.Resto;
import com.voting.model.User;
import com.voting.model.Vote;
import com.voting.testdata.RestoTestData;
import com.voting.util.exception.PastDateException;
import com.voting.util.exception.TooLateEcxeption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.voting.testdata.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ActiveProfiles("impl")
public class VoteServiceTest extends AbstractServiceTest{
    private final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    private VoteService service;

    @Autowired
    private UserService userService;

    @Autowired
    private RestoService restoService;


    @Test
    public void create() throws ParseException {
        User user = ADMIN;
        Resto resto = TestUtil.getByName(RestoTestData.restos, "Ресторан 2");
        LocalDate ld = LocalDate.now().plusDays(1);

        Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Vote vote = new Vote(null, user, resto, date, LocalDateTime.now());
        service.create(vote, user.getId());
    }

    @Test //(expected = TooLateEcxeption.class)
    public void update() {
        assertThrows(PastDateException.class, () -> {

            User user = ADMIN;
            Resto resto = TestUtil.getByName(RestoTestData.restos, "Ресторан 2");
            Date date = SDF.parse("02-11-2018");

            Vote vote = new Vote(user, resto, date, LocalDateTime.now());
            service.create(vote, 100001);

            Vote newVote = service.getByDate(date, user.getId());
            newVote.setResto(TestUtil.getByName(RestoTestData.restos, "Ресторан 3"));
            date = SDF.parse("01-11-2018");
            newVote.setDate(date);
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
        assertEquals(service.getAll(ADMIN_ID).size(), 3);
    }


    @Test
    public void getByDate() throws ParseException {
        Date date = SDF.parse("21-11-2018");
        Vote vote = service.getByDate(date, USER_ID);
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