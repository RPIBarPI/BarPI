package rpi.barpi;

import java.util.ArrayList;

public class Bar //basic information on bars
{
    private int id=0;
    private String name="";
    private String description="";
    private float rating;
    public ArrayList<Message> messages =new ArrayList<Message>();
    private Location location=new Location();
    public ArrayList<Drink> drinks=new ArrayList<Drink>();//the menu
    public ArrayList<Event> events=new ArrayList<Event>();
    public ArrayList<Special> specials=new ArrayList<Special>();

    //constructors
    public Bar(int newID, String newName, String newDescription, float newRating, Location newLocation)
    {
        id=newID;
        name=newName;
        description=newDescription;
        rating=newRating;
        location=new Location(newLocation);//Using value not ref
    }

    //gets
    public int getID() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public float getRating() { return rating; }
    public Location getLocation() { return location; }

    //sets
    public void setName(String newName) { name=newName; }
    public void setDescription(String newDescription) { description=newDescription; }
    public void setRating(float newRating) { rating=newRating; }
    public void setLocation(Location newLocation) { location=newLocation; }
    public void setMessages(ArrayList<Message> newMsgs) { messages=new ArrayList<Message>(newMsgs); }//make a copy

    //other
    @Override
    public String toString()
    {
        String retVal="";

        //the name
        retVal+=name+'\n';
        retVal+=description;

        return retVal;
    }

    @Override
    public boolean equals(Object obj)
    {
        Bar otherBar=(Bar)obj;

        return (id == otherBar.id);
    }
}
