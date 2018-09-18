package ru.scapegoats.dayplanner.activity.main;

import android.util.Log;

import java.util.Date;

public class CardContent {
    private String time;

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public int getIntTime(){
        //method returns int value from a string that contain time
        //time pattern 00 : 00 ([0]0[1]0[2] [3]:[4] [5]0[6]0)
        char[] array=time.toCharArray();

        return Integer.parseInt(String.valueOf(array[0]) + array[1] + array[5] + array[6]);
    }

    private String text;

    public CardContent(String time, String text) {
        this.time = time;
        this.text = text;
    }

    @Override
    public String toString() {
        return time+"&&"+text;
    }
}
