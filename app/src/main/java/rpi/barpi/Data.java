package rpi.barpi;

import android.content.SharedPreferences;

import java.util.ArrayList;

public class Data
{
    public static final String VERSION="0.01";

    public static boolean initialized=false;

    public static SharedPreferences saveData;

    public static MainActivity mainAct=null;
    public static BarActivity barAct=null;

    public static int exampleInteger;

    public static ArrayList<Bar> bars=new ArrayList<Bar>();

    public static void init(SharedPreferences tempSP)
    {
        saveData=tempSP;
        loadData();
        initialized=true;
    }

    private static void loadData()
    {
        exampleInteger=saveData.getInt("exampleinteger", -1);
    }

    public static void saveData()
    {
        SharedPreferences.Editor SDEditor=saveData.edit();
        SDEditor.putInt("exampleinteger", exampleInteger);
        SDEditor.apply();
    }

}
