package com.example.user.hitmillionaire11;

import java.util.Comparator;

public class ScoreComrator implements Comparator<User> {


    @Override
    public int compare(User o1, User o2) {
        int x =  Integer.parseInt(o1.getScore());
        int y =  Integer.parseInt(o2.getScore());
        return Integer.compare(x,y);
    }
}
