package rpi.barpi;

import java.util.ArrayList;

public class Event
{
    private int id=-1;
    private String name="";
    private String description="";
    public ArrayList<Message> messages =new ArrayList<Message>();

    //constructors
    public Event()
    {
        id=-1;
        name="";
        description="";
        messages =new ArrayList<Message>();
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
        retVal+=description;
        return retVal;
    }
}
