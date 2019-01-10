package com.voting;

import com.voting.model.Dish;
import com.voting.model.Role;
import com.voting.model.User;
import com.voting.model.Vote;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        /*String uploadedFolder = System.getenv("VOTING_ROOT");
        System.out.println(uploadedFolder);*/
        String s[] = "chiken_grille.jpg".split("\\.");
        String ss = s[1];
        System.out.println(ss);
        //new Ttt();
    }
}

class Ttt {
    public Ttt() {
        System.out.println(this.getClass().getClassLoader().getResource("images"));

    }
}