package com.voting.web.dailyMenu;

import com.voting.web.AbstractControllerTest;
import com.voting.web.vote.AbstractVoteController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;

public class AbstractDailyMenuControllerTest extends AbstractControllerTest {

    @Autowired
    private AbstractDailyMenuController controller;

    @Autowired
    private AbstractVoteController voteRestController;

    @Test
    public void get() throws ParseException {
        /*
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2018-11-11");
        DailyMenuUtil.convertToDailyMenuTo(controller.getByDate(date), voteRestController.getByDate(date)).forEach(System.out::println);
        */
    }


    @Test
    public void delete() {
    }

    @Test
    public void create() {
    }

    @Test
    public void update() {
    }

    @Test
    public void getByDate() {
    }
}