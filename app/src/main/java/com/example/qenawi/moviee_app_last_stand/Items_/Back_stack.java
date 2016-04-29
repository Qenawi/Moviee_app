package com.example.qenawi.moviee_app_last_stand.Items_;

import java.io.Serializable;
import java.util.Stack;

/**
 * Created by QEnawi on 4/25/2016.
 */
public class Back_stack implements Serializable {
    public Stack<String> getFragments() {
        return Fragments;
    }

    public void setFragments(Stack<String> fragments) {
        Fragments = fragments;
    }

    Stack<String>Fragments;

}
