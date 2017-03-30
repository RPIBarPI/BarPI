package rpi.barpi;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by eryka on 3/29/2017.
 */

public class EventAdapter extends ArrayAdapter<Event> {
    private Context context;
    int resource;
    private ArrayList<Event> events = new ArrayList<Event>();

    public EventAdapter(Context context, int resource, ArrayList<Event> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.events=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(resource, null);
        Event currentEvent=events.get(position);

        //ImageView img = (ImageView)view.findViewById(R.id.event_img);

        TextView eventTitle = (TextView)view.findViewById(R.id.event_title);
        TextView eventDesc = (TextView)view.findViewById(R.id.event_description);

        // Data.bars.get(Data.barAct.contextMenuItemSelected) == Data.barAct.bar.events
        // Go thru all bars in list to lay them out
        for (int i=0; i<Data.barAct.bar.events.size(); ++i) {
            if(Data.barAct.bar.events.get(i).getID() == currentEvent.getID()) {
                //img.setImageDrawable(Drawable);
                //barDesc.setText(Data.barAct.bar.getDescription());
                eventTitle.setText(Data.barAct.bar.events.get(i).getName());
                eventDesc.setText(Data.barAct.bar.events.get(i).getDescription().toString());
                break;
            }

        }

        if (position%2 == 1)
            view.setBackgroundColor(Color.WHITE);
        else
            view.setBackgroundColor(ResourcesCompat.getColor(view.getResources(), R.color.colorRedish, null));//red-ish

        return view;
    }
}
