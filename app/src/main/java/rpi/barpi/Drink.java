package rpi.barpi;

public class Drink
{
    private int id=-1;
    private String name="";
    private String description="";
    private float price=0.0f;
    //private int barid=-1;//dont need to know this here, need to know it in the database

    //constructors
    public Drink()
    {
        id=-1;
        name="";
        description="";
        price=0.0f;
    }

    public Drink(int newID, String newName, String newDescription, float newPrice)
    {
        id=newID;
        name=newName;
        description=newDescription;
        price=newPrice;
    }

    //gets
    public int getID() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public float getPrice() { return price; }

    //sets
    public void setName(String newName) { name=newName; }
    public void setDescription(String newDescription) { description=newDescription; }
    public void setPrice(float newPrice) { price=newPrice; }
}
