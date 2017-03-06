package rpi.barpi;

public class Special extends Event
{
    private int drinkID=-1;
    private float price=0.0f;

    //constructors
    public Special()
    {
        super();
        drinkID=-1;
        price=0.0f;
    }

    public Special(int newID, String newName, String newDescription)
    {
        super(newID, newName, newDescription);
        drinkID=-1;
        price=0.0f;
    }

    public Special(int newID, String newName, String newDescription, int newDrinkID, float newPrice)
    {
        super(newID, newName, newDescription);
        drinkID=newDrinkID;
        price=newPrice;
    }

    //gets
    public int getDrinkID() { return drinkID; }
    public float getPrice() { return price; }

    //sets
    public void setDrinkID(int newDrinkID) { drinkID=newDrinkID; }
    public void setPrice(float newPrice) { price=newPrice; }

    //other
    @Override
    public Boolean isSpecial() { return true; }
}
