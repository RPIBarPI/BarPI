package rpi.barpi;

import java.util.ArrayList;

public class Event
{
    private int id=-1;
    private String name="";
    private String description="";
    private float rating;
    public ArrayList<Message> messages =new ArrayList<Message>();

    //constructors
    public Event()
    {
        id=-1;
        name="";
        description="";
        rating=0.0f;
        messages =new ArrayList<Message>();
    }

    public Event(int newID, String newName, String newDescription, float newRating)
    {
        id=newID;
        name=newName;
        description=newDescription;
        rating=newRating;
    }

    //gets
    public int getID() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public float getRating() { return rating; }

    //sets
    public void setName(String newName) { name=newName; }
    public void setDescription(String newDescription) { description=newDescription; }
    public void setRating(float newRating) { rating=newRating; }

    @Override
    public String toString() {
        String retVal="";

        //the name
        retVal+=name+'\n';
        retVal+=description;
        return retVal;
    }
}
