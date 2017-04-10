package rpi.barpi;

public class Location
{
    private String aptsuite="";//apartment or suite
    private String street="";
    private String city="";
    private String state="";
    private String zip="";
    private String country="";
    private float longitude=360.0f;
    private float latitude=360.0f;

    //constructors
    public Location()
    {
        aptsuite="";
        street="";
        city="";
        state="";
        zip="";
        country="";
        longitude=360.0f;
        latitude=360.0f;
    }

    public Location(String newAptSuite, String newStreet, String newCity, String newState, String newZip, String newCountry,
                    float newLong, float newLat)
    {
        aptsuite=newAptSuite;
        street=newStreet;
        city=newCity;
        state=newState;
        zip=newZip;
        country=newCountry;
        longitude=newLong;
        latitude=newLat;
    }

    public Location(Location location2)
    {
        aptsuite=location2.aptsuite;
        street=location2.street;
        city=location2.city;
        state=location2.state;
        zip=location2.zip;
        country=location2.country;
        longitude=location2.longitude;
        latitude=location2.latitude;
    }

    //gets
    public String getAptsuite() { return aptsuite; }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZip() { return zip; }
    public String getCountry() { return country; }
    public float getLongitude() { return longitude; }
    public float getLatitude() { return latitude; }

    //sets
    public void setAptsuite(String newAptSuite) { aptsuite=newAptSuite; }
    public void setStreet(String newStreet) { street=newStreet; }
    public void setCity(String newCity) { city=newCity; }
    public void setState(String newState) { state=newState; }
    public void setZip(String newZip) { zip=newZip; }
    public void setCountry(String newCountry) { country=newCountry; }
    public void setLongitude(float newLong) { longitude=newLong; }
    public void setLatitude(float newLat) { latitude=newLat; }

    @Override
    public String toString()
    {
        String retVal="";

        //the name
        if (!aptsuite.isEmpty()) retVal+=aptsuite+'\n';
        if (!street.isEmpty()) retVal+=street+'\n';
        if (!city.isEmpty()) retVal+=city+", ";
        if (!state.isEmpty()) retVal+=state+' ';
        if (!zip.isEmpty()) retVal+=zip;

        if(retVal.isEmpty())
        {
            if((longitude != 360.0f) && (latitude != 360.0f))
                retVal+=Float.toString(longitude)+", "+Float.toString(latitude);
        }

        if(retVal.isEmpty())
        {
            retVal="No location provided";
        }

        return retVal;
    }
}
