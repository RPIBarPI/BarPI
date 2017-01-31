package rpi.barpi;

public class Bar //basic information on bars
{
    private int id=0;
    private String name="";

    public Bar(int newID, String newName)
    {
        id=newID;
        name=newName;
    }

    public int getID() { return id; }
    public String getName() { return name; }


    public void setID(int newID) { id=newID; }
    public void setName(String newName) { name=newName; }

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
