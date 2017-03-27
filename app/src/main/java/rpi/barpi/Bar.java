package rpi.barpi;

import java.util.ArrayList;

public class Bar //basic information on bars
{
    private int id=0;
    private String name="";
    private float rating;
    public ArrayList<Comment> comments=new ArrayList<Comment>();
    private Location location=new Location();
    public static ArrayList<Event> events=new ArrayList<Event>();//includes specials
    public ArrayList<Drink> drinks=new ArrayList<Drink>();//the menu

    //constructors
    public Bar(int newID, String newName, float newRating, Location newLocation)
    {
        id=newID;
        name=newName;
        rating=newRating;
        location=new Location(newLocation);//Using value not ref
    }

    //gets
    public int getID() { return id; }
    public String getName() { return name; }
    public float getRating() { return rating; }
    public Location getLocation() { return location; }

    //sets
    public void setName(String newName) { name=newName; }
    public void setRating(float newRating) { rating=newRating; }
    public void setLocation(Location newLocation) { location=newLocation; }

    //other
    @Override
    public String toString()
    {
        String retVal="";

        //the name
        retVal+=name+'\n';
        retVal+="Located at dfijhfdg";

        return retVal;
    }

    @Override
    public boolean equals(Object obj)
    {
        Bar otherBar=(Bar)obj;

        return (id == otherBar.id);
    }
}
