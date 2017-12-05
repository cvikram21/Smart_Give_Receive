package com.mydonation;

import java.util.ArrayList;

public class CalendarCollection {
    public String date="";
    public String event_message="";
    public String RequestedId="";

    public static ArrayList<CalendarCollection> date_collection_arr;
    public CalendarCollection(String date,String event_message,String RequestedId){

        this.date=date;
        this.event_message=event_message;
        this.RequestedId=RequestedId;

    }

}