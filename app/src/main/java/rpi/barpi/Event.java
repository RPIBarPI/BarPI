package rpi.barpi;

import java.util.ArrayList;

public class Event
{
    private int id=-1;
    private String name="";
    private String description="";
    public ArrayList<Comment> comments=new ArrayList<Comment>();

    //constructors
    public Event()
    {
        id=-1;
        name="";
        description="";
        comments=new ArrayList<Comment>();
    }

    public Event(int newID, String newName, String newDescription)
    {
        id=newID;
        name=newName;
        description=newDescription;
    }

    //gets
    public int getID() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    //sets
    public void setName(String newName) { name=newName; }
    public void setDescription(String newDescription) { description=newDescription; }

    //other
    public Boolean isSpecial() { return false; }

    @Override
    public String toString() {
        String retVal="";

        //the name
        retVal+=name+'\n';
        retVal+="Event description blah blah blah";
        return retVal;
    }
}
