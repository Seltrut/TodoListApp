package com.example.matt.taskslistapp;

import java.util.ArrayList;

/**
 * Created by Matt on 12/18/2017.
 */

public class todos {

    public String title;
    public String desc;
    public boolean complete;

    public todos(){
        title ="";
        desc = "";
        complete = false;
    }

    public todos( String t, String d, boolean c ){
        title = t.substring(0,1).toUpperCase()+t.substring(1);
        desc = d;
        complete = c;
    }

    public void setTitle( String t ){
        title = t;
    }

    public ArrayList<todos> getTodos(){
        ArrayList<todos> samples = new ArrayList<todos>();
        samples.add( new todos( "groceries", " go to store and buy things", false));
        samples.add( new todos("soccer", "take kids to soccer", true));

        return samples;
    }


}
