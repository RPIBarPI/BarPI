package rpi.barpi;

public class Special extends Event
{
    private int eventID=-1;
    private int drinkID=-1;
    private float price=0.0f;

    //constructors
    public Special()
    {
        super();
        eventID=-1;
        drinkID=-1;
        price=0.0f;
    }

    public Special(int newID, String newName, String newDescription)
    {
        super(newID, newName, newDescription);
        eventID=-1;
        drinkID=-1;
        price=0.0f;
    }

    public Special(int newID, String newName, String newDescription, int newEventID, int newDrinkID, float newPrice)
    {
        super(newID, newName, newDescription);
        eventID=newEventID;
        drinkID=newDrinkID;
        price=newPrice;
    }

    //gets
    public int getEventID() { return eventID; }
    public int getDrinkID() { return drinkID; }
    public float getPrice() { return price; }

    //sets
    public void setEventID(int newEventID) { eventID=newEventID; }
    public void setDrinkID(int newDrinkID) { drinkID=newDrinkID; }
    public void setPrice(float newPrice) { price=newPrice; }
}
