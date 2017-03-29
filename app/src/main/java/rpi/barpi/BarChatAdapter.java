package rpi.barpi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BarChatAdapter extends ArrayAdapter<Message>
{
    private Context context;
    int resource;
    private ArrayList<Message> messages=new ArrayList<Message>();

    public BarChatAdapter(Context context, int resource, ArrayList<Message> objects)
    {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.messages=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(resource, null);

        Message currentMsg=messages.get(position);

        TextView sender=(TextView)view.findViewById(R.id.msg_sender);
        TextView message=(TextView)view.findViewById(R.id.msg_message);

        String senderName="Name";

        sender.setText(senderName);
        message.setText(currentMsg.getComment());

        if (position%2 == 1)
            view.setBackgroundColor(Color.WHITE);
        else
            view.setBackgroundColor(Color.rgb(0xFF, 0x00, 0x00));//red

        return view;
    }
}