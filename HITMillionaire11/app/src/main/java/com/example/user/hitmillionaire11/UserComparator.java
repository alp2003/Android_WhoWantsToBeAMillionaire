package com.example.user.hitmillionaire11;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        String x = o1.getUsername();
        String y =  o2.getUsername();
        return x.compareTo(y);
    }
}
