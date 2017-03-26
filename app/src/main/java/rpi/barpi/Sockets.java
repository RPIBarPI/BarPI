package rpi.barpi;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Sockets
{
    private static Socket sock;
    private static final String serverIP="192.168.1.170";
    private static final int mainPort=45019;
    private static DataOutputStream out;
    private static DataInputStream in;

    //tasks

    public static class connectSocketTask extends AsyncTask<ArrayList<String>, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(ArrayList<String>... arg0)
        {
            openSocket();
            if(arg0.length > 0)
            {
                if(write(arg0[0]))
                {
                    return read();
                }
            }
            ArrayList<String> data=new ArrayList<String>();
            return data;
        }

        protected void onPostExecute(ArrayList<String> res)
        {
            /*if(res.size() == 0) Data.mainAct.loginFailed();
            else Data.mainAct.loginSuccessful(res);*/
        }
    }

    public static class readSocketTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0)
        {
            while(sock != null && sock.isConnected() && !isCancelled())
            {
                ArrayList<String> data=read();
                if(data.size() == 0) continue;//disconnected
                String command=data.get(0);
                data.remove(0);

				/*
				 * E: Error/logout
				 * I: Initial data
				 */

                //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                if(command.equals("E"))//error/logout
                {
                    //server error, do something
                }
                else if(command.equals("I"))//Initial User data
                {
                    //
                }
            }
            return null;
        }
    }

    //the class

    //call methods

    public static void connect(ArrayList<String> data)
    {
        new connectSocketTask().execute(data);
    }

    public static void readEngine()//read loop
    {
        new readSocketTask().execute();
    }

    public static void writeEngine(final ArrayList<String> data)//write to a socket
    {
        Runnable writeRun=new Runnable()
        {
            public void run()
            {
                if(!write(data))//add the sid
                    closeSocket();
            }
        };
        Thread writerThread=new Thread(writeRun);
        writerThread.start();
    }

    //private functions

    private static boolean isEscaped(final int index, final String text)
    {
        int counter=0;
        while((index > 0) && (text.charAt(index-counter-1) == '%'))
            ++counter;
        return (counter%2 != 0);
    }

    private static int getNonEscapedDelimiter(final String text, final String delimiter)
    {
        int breakPoint=-1;
        do
        {
            if(breakPoint == -1) breakPoint=text.indexOf(delimiter);
            else breakPoint=text.indexOf(delimiter, breakPoint+delimiter.length());
        } while(isEscaped(breakPoint, text));
        return breakPoint;
    }

    private static boolean openSocket()
    {
        try
        {
            sock=new Socket(serverIP, mainPort);
            out=new DataOutputStream(sock.getOutputStream());
            in=new DataInputStream(sock.getInputStream());
            return true;
        }
        catch(IOException ex)
        {
            //could not connect to server
            closeSocket();
            return false;
        }
    }

    private static ArrayList<String> read()
    {
        ArrayList<String> data=new ArrayList<String>();

        String text="";
        try
        {
            int charRead=0;
            boolean endOfPacket=false;
            while((charRead != -1) && (!endOfPacket))
            {
                charRead=in.read();
                if(charRead != -1) text+=Character.toString((char)charRead);
                if((text.length() > 2) && (text.substring(text.length()-2).equals("\\|")))
                    endOfPacket=true;
            }
        }
        catch(IOException ex)
        {
            closeSocket();
            return data;
        }
        //text=encrypt(text);

        int breakPoint=getNonEscapedDelimiter(text, "|");

        do
        {
            int breakPoint2=getNonEscapedDelimiter(text, "\\|");

            if((breakPoint == -1) || (breakPoint2 == -1))
                break;

            if(breakPoint == breakPoint2+1)//reached the end
                data.add(text.substring(0, breakPoint2));
            else
                data.add(text.substring(0, breakPoint));

            text=text.substring(breakPoint+1);

            breakPoint=getNonEscapedDelimiter(text, "|");

        } while(breakPoint > 0);

        for(int i=0;i<data.size();++i)
        {
            //unescape the characters
            String escText=data.get(i);

            for(int j=0;j<escText.length();++j)
            {
                if(escText.charAt(j) == '%')
                    escText=escText.substring(0, j)+escText.substring(j+1);
            }
            if(data.get(i).length() != escText.length()) data.set(i, escText);
        }

        return data;
    }

    private static boolean write(ArrayList<String> data)
    {
        if(out == null) return false;

        final String options="%\\|";

        String text="";
        for(int i=0;i<data.size();++i)
        {
            //escape the characters
            String escText=data.get(i);
            for(int j=0;j<escText.length();++j)
            {
                int breakPoint=options.indexOf(escText.charAt(j));
                if(breakPoint != -1)
                {
                    escText=escText.substring(0, j)+"%"+escText.substring(j);
                    ++j;
                }
            }
            if(i < data.size()-1) text+=escText+"|";
            else text+=escText+"\\|";
        }

        try
        {
            //text=encrypt(text);
            byte[] buf=text.getBytes("UTF-8");
            out.write(buf, 0, buf.length);

            //user logging out? close the socket
            if(text.length() > 0 && text.substring(0, 1).equals("B"))
                closeSocket();
            return true;
        }
        catch(IOException ex)
        {
            closeSocket();
            return false;
        }
    }

    public static void closeSocket()
    {
        try
        {
            if(out != null) out.close();
            if(in != null) in.close();
            if((sock != null) && (sock.isConnected())) sock.close();
        }
        catch(IOException ex) { }
    }

}