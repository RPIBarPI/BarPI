package rpi.barpi;

import android.content.SharedPreferences;

import java.util.ArrayList;

public class Data
{
    public static final String VERSION="0.03";

    public static boolean initialized=false;

    public static SharedPreferences saveData;

    public static MainActivity mainAct=null;
    public static BarActivity barAct=null;
    public static BarChatActivity barChatAct=null;

    public static int regUserID;

    public static ArrayList<Bar> bars=new ArrayList<Bar>();

    public static void init(SharedPreferences tempSP)
    {
        saveData=tempSP;
        loadData();
        initialized=true;
    }

    private static void loadData()
    {
        regUserID=saveData.getInt("regUserID", 0);
    }

    public static void saveData()
    {
        SharedPreferences.Editor SDEditor=saveData.edit();
        SDEditor.putInt("regUserID", regUserID);
        SDEditor.apply();
    }


    public static void addBar(final Bar newBar)
    {
        if(mainAct != null)
        {
            mainAct.runOnUiThread(new Runnable()
            {
                public void run()
                {
                    bars.add(newBar);
                    mainAct.barListItems.add(newBar);
                    mainAct.appAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public static void addDrink(final int barIndex, final Drink newDrink)
    {
        if(mainAct != null)
        {
            mainAct.runOnUiThread(new Runnable()
            {
                public void run()
                {
                    bars.get(barIndex).drinks.add(newDrink);
                    if(barAct != null) barAct.drinkAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public static void addEvent(final int barIndex, final Event newEvent)
    {
        if(mainAct != null)
        {
            mainAct.runOnUiThread(new Runnable()
            {
                public void run()
                {
                    bars.get(barIndex).events.add(newEvent);
                    if(barAct != null) barAct.eventAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public static void addSpecial(final int barIndex, final Special newSpecial)
    {
        if(mainAct != null)
        {
            mainAct.runOnUiThread(new Runnable()
            {
                public void run()
                {
                    bars.get(barIndex).specials.add(newSpecial);
                    if(barAct != null) barAct.drinkAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public static void addMessage(final int barIndex, final int eventid, final Message newMsg)
    {
        if(barChatAct != null)
        {
            barChatAct.runOnUiThread(new Runnable()
            {
                public void run()
                {
                    if(eventid == 0)
                    {
                        bars.get(barIndex).messages.add(newMsg);
                        barChatAct.appAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        for(int i=0;i<bars.get(barIndex).events.size();++i)
                        {
                            if(eventid == bars.get(barIndex).events.get(i).getID())
                            {
                                bars.get(barIndex).events.get(i).messages.add(newMsg);
                                //eventChatAct.appAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }
                }
            });
        }
    }
}
