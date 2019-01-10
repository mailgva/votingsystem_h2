package com.voting.web.voting;

import com.voting.model.Resto;
import com.voting.model.User;
import com.voting.model.Vote;
import com.voting.service.DailyMenuService;
import com.voting.service.RestoService;
import com.voting.service.UserService;
import com.voting.service.VoteService;
import com.voting.to.DailyMenuTo;
import com.voting.util.DailyMenuUtil;
import com.voting.web.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public abstract class AbstractVotingController {
    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private VoteService voteService;

    @Autowired
    private DailyMenuService dailyMenuService;

    @Autowired
    private RestoService restoService;

    @Autowired
    private UserService userService;


    public String getDailyMenu(HttpServletRequest request, Model model) {
        return getDailyMenuByDate(request, model);
    }

    public String getDailyMenuFiltered(HttpServletRequest request, Model model)  {
        return getDailyMenuByDate(request, model);
    }

    private Date setParameterDate(Date date, HttpServletRequest request)  {


        if(request.getParameter("date") != null) {
            try {
                date = DATE_FORMAT.parse(request.getParameter("date"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }

    protected String getDailyMenuByDate(HttpServletRequest request, Model model) {
        int userId = SecurityUtil.authUserId();

        Date date = null;

        date = setParameterDate(date, request);

        if(date==null) {
            date = new Date();
            Calendar c = Calendar.getInstance();
            if(LocalDateTime.now().getHour() >= 11) {
                c.setTime(date);
                c.add(Calendar.DATE, 1);
                date = c.getTime();
            }
        }

        Vote vote = voteService.getByDate(date, userId);

        model.addAttribute("userName", SecurityUtil.authUserName());
        model.addAttribute("isAdmin", userService.get(userId).isAdmin());
        model.addAttribute("voteId", (vote == null ? null : vote.getId()));
        model.addAttribute("dateMenu", DATE_FORMAT.format(date));
        model.addAttribute("dailyMenus", getDailyMenuTo(date, vote));

        return "dailymenu";
    }

    public List<DailyMenuTo> getDailyMenu(Date date) {
        int userId = SecurityUtil.authUserId();
        Vote vote = voteService.getByDate(date, userId);
        return getDailyMenuTo(date, vote);
    }

    protected List<DailyMenuTo> getDailyMenuTo(Date date, Vote vote) {
        return DailyMenuUtil.convertToDailyMenuTo(date, dailyMenuService.getByDate(date), vote);
    }

    public String setVote(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Integer restId = request.getParameter("restoId").isEmpty() ? null : Integer.parseInt(request.getParameter("restoId"));

        Date date = null;
        date = setParameterDate(date, request);

        Integer voteId = request.getParameter("voteId").isEmpty() ? null : Integer.parseInt(request.getParameter("voteId"));

        //setUserVote(date, restId);

        model.addAttribute("dateMenu", DATE_FORMAT.format(date));
        return "forward:/voting";
    }

    public void setUserVote(Date date, Resto resto /*Integer restoId*/)  {
        int userId = SecurityUtil.authUserId();

        Vote vote   = voteService.getByDate(date, userId);
        //Resto resto = restoService.get(restoId);
        User user   = userService.get(userId);

        if(vote == null) {
            vote = new Vote(null, user, resto, date, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        } else {
            vote.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            vote.setResto(resto);
        }

        if (vote.getId() == null) {
            voteService.create(vote, userId);
        } else {
            voteService.update(vote, userId);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }


}
