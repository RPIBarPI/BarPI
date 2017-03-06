package rpi.barpi;

public class Location
{
    private String aptsuite="";//apartment or suite
    private String street="";
    private String city="";
    private String state="";
    private String zip="";
    private String country="";

    //constructors
    public Location()
    {
        aptsuite="";
        street="";
        city="";
        state="";
        zip="";
        country="";
    }

    public Location(String newAptSuite, String newStreet, String newCity, String newState, String newZip, String newCountry)
    {
        aptsuite=newAptSuite;
        street=newStreet;
        city=newCity;
        state=newState;
        zip=newZip;
        country=newCountry;
    }

    public Location(Location location2)
    {
        aptsuite=location2.aptsuite;
        street=location2.street;
        city=location2.city;
        state=location2.state;
        zip=location2.zip;
        country=location2.country;
    }

    //gets
    public String getAptsuite() { return aptsuite; }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZip() { return zip; }
    public String getCountry() { return country; }

    //sets
    public void setAptsuite(String newAptSuite) { aptsuite=newAptSuite; }
    public void setStreet(String newStreet) { street=newStreet; }
    public void setCity(String newCity) { city=newCity; }
    public void setState(String newState) { state=newState; }
    public void setZip(String newZip) { zip=newZip; }
    public void setCountry(String newCountry) { country=newCountry; }
}
