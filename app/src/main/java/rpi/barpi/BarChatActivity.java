package rpi.barpi;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BarChatActivity extends AppCompatActivity
{
    ListView lvmessages;
    EditText messagebox;
    BarChatAdapter appAdapter=null;
    Bar bar=Data.bars.get(Data.mainAct.contextMenuItemSelected);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchat);

        //set the title
        setTitle(bar.getName());

        //for updating the chat
        Data.barChatAct=this;

        //set the gui objects
        lvmessages=(ListView)findViewById(R.id.messages_list);
        messagebox=(EditText)findViewById(R.id.messagetextedit);

        //get the messages
        loadMessages();

        //set the lvs array
        appAdapter=new BarChatAdapter(this, R.layout.layout_message, bar.messages);
        lvmessages.setAdapter(appAdapter);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        saveMessages();
        Data.barChatAct=null;
    }

    protected void loadMessages()
    {
        String fname="BarChat"+Integer.toString(bar.getID())+"Event0";
        int fromTime=0;

        try
        {
            FileInputStream fos=openFileInput(fname);
            String contents="";
            int b=0;
            while((b=fos.read()) != -1)
                contents+=Character.toString((char)b);
            fos.close();

            ArrayList<Message> newMsgs=new ArrayList<Message>();
            String[] strNewMsgs=contents.split("\n");

            for(int i=0;i<strNewMsgs.length;++i)
            {
                //get the account id
                int breakPoint=strNewMsgs[i].indexOf('|');
                if(breakPoint == -1) continue;
                int accountid=Integer.parseInt(strNewMsgs[i].substring(0, breakPoint));
                strNewMsgs[i]=strNewMsgs[i].substring(breakPoint+1);
                //get the ip
                breakPoint=strNewMsgs[i].indexOf('|');
                if(breakPoint == -1) continue;
                String ip=strNewMsgs[i].substring(0, breakPoint);
                strNewMsgs[i]=strNewMsgs[i].substring(breakPoint+1);
                //get the timestamp
                breakPoint=strNewMsgs[i].indexOf('|');
                if(breakPoint == -1) continue;
                int timestamp=Integer.parseInt(strNewMsgs[i].substring(0, breakPoint));
                strNewMsgs[i]=strNewMsgs[i].substring(breakPoint+1);
                //get the message
                if(breakPoint == -1) continue;
                String newMessage=strNewMsgs[i];

                newMsgs.add(new Message(accountid, "ip", newMessage, timestamp));
            }
            bar.setMessages(newMsgs);

            if(bar.messages.size() > 0)
                fromTime=bar.messages.get(bar.messages.size()-1).getTimestamp();
        }
        catch(IOException ex)
        {
            //guess the messages wont save
        }

        ArrayList<String> data=new ArrayList<String>();
        data.add("4");
        data.add(Integer.toString(bar.getID()));
        data.add("0");
        data.add(Integer.toString(fromTime));

        Sockets.writeEngine(data);
    }

    protected void saveMessages()
    {
        //save the messages
        String fname="BarChat"+Integer.toString(bar.getID())+"Event0";
        String contents="";

        for(int i=0;i<bar.messages.size();++i) contents+=bar.messages.get(i).saveFormat()+"\n";

        try
        {
            FileOutputStream fos=openFileOutput(fname, Context.MODE_PRIVATE);
            fos.write(contents.getBytes());
            fos.close();
        }
        catch(IOException ex)
        {
            //guess the messages wont save
        }
    }

    public void btnsend(View v)
    {
        String message=messagebox.getText().toString();
        if((message.length() < 1) || (message.length() > 200)) return;
        int barid=bar.getID();

        ArrayList<String> data=new ArrayList<String>();
        data.add("3");
        data.add(Integer.toString(barid));
        data.add("0");
        data.add(message);

        Sockets.writeEngine(data);
        messagebox.setText("");
    }
}