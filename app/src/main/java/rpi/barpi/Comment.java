package rpi.barpi;

public class Comment
{
    private int id=-1;
    private String ip="";
    private String comment="";
    private int timestamp=0;

    //constructor
    public Comment(int newID, String newIP, String newComment, int newTimestamp)
    {
        id=newID;
        ip=newIP;
        comment=newComment;
        timestamp=newTimestamp;
    }

    //gets
    public int getID() { return id; }
    public String getIP() { return ip; }
    public String getComment() { return comment; }
    public int getTimestamp() { return timestamp; }

    //no sets
}
