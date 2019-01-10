package com.voting.web.voting;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


//@RequestMapping(value = "/voting")
//@Controller
public class JspVotingController extends AbstractVotingController{

    @GetMapping
    public String getDailyMenu(HttpServletRequest request, Model model) {
        return super.getDailyMenu(request, model);
    }

    @PostMapping
    public String getDailyMenuFiltered(HttpServletRequest request, Model model)  {
        return super.getDailyMenuFiltered(request, model);
    }

    @PostMapping("/vote")
    public String setVote(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        return super.setVote(request, model);
    }

}
