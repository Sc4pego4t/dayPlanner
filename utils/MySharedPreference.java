package ru.scapegoats.dayplanner.utils;

import android.content.SharedPreferences;
import android.util.ArraySet;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.scapegoats.dayplanner.activity.main.CardContent;
import ru.scapegoats.dayplanner.activity.main.MainView;
import ru.scapegoats.dayplanner.modules.AbstractActivity;

public class MySharedPreference {
    private final static String TODO_LIST="list";
    private final static String KEY_TEXT="text";
    private final static String KEY_TIME="time";
    private final static String DIVIDER="#~&";


    public static List<CardContent> getTodoList(AbstractActivity activity){
        SharedPreferences preferences=activity.getSharedPreferences(TODO_LIST,0);
        List<CardContent> list = new ArrayList<>();

        List<String> text = null;
        List<String> time = null;

        try {

            text = Arrays.asList(preferences.getString(KEY_TEXT, null).split(DIVIDER));
            time = Arrays.asList(preferences.getString(KEY_TIME, null).split(DIVIDER));

            if(text.get(0).equals(""))
                throw new NullPointerException();

            for (int i=0;i<text.size();i++){
                list.add(new CardContent(time.get(i),text.get(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("qwe",list.size()+"");

        return list;
    }

    private static void saveListToPreference(List<CardContent> list, AbstractActivity activity){
        //sort and save list
        SharedPreferences.Editor editor=activity.getSharedPreferences(TODO_LIST,0).edit();

        StringBuilder text = new StringBuilder();
        StringBuilder time = new StringBuilder();


        //bubble sort
        for(int i=0; i<list.size();i++){
            for(int j=0;j<list.size()-1;j++){
                CardContent tempCard;
                if(list.get(j).getIntTime()>list.get(j+1).getIntTime()){
                    tempCard=list.get(j);
                    list.set(j,list.get(j+1));
                    list.set(j+1,tempCard);
                }
            }
        }

        for (CardContent card : list) {
            //do not add divider to a last element

            text.append(card.getText()).append(DIVIDER);
            time.append(card.getTime()).append(DIVIDER);
        }

        editor.putString(KEY_TEXT,text.toString());
        editor.putString(KEY_TIME, time.toString());
        editor.apply();
    }

    public static void deleteElementFromList(int position, AbstractActivity activity){
        List<CardContent> list=getTodoList(activity);
        list.remove(position);
        saveListToPreference(list,activity);

    }
    public static void addElementToList(CardContent newElement, AbstractActivity activity){
        List<CardContent> list= getTodoList(activity);
        list.add(newElement);
        saveListToPreference(list,activity);

    }
    public static void changeElementFromList(CardContent newElement,int position, AbstractActivity activity){

        List<CardContent> list= getTodoList(activity);
        list.set(position,newElement);
        Log.e("asd","CHAANGE");
        saveListToPreference(list,activity);

    }



}
