package rpi.barpi;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class BarAdapter extends ArrayAdapter<Bar> {
    private Context context;
    int resource;
    private ArrayList<Bar> bars = new ArrayList<Bar>();

    public BarAdapter(Context context, int resource, ArrayList<Bar> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.bars=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(resource, null);
        Bar currentBar=bars.get(position);

        ImageView img = (ImageView)view.findViewById(R.id.bar_img);
        TextView barTitle = (TextView)view.findViewById(R.id.bar_title);
        TextView barLoc = (TextView)view.findViewById(R.id.bar_location);

        String barName="";
        String barLocation="";
        Location barL;

        // Go thru all bars in list to lay them out
        for (int i=0; i<Data.bars.size(); ++i) {
            if(Data.bars.get(i).getID() == currentBar.getID()) {
                barName = Data.bars.get(i).getName();
                barL = Data.bars.get(i).getLocation();
                barLocation = barL.getAptsuite()+ " oh herro "+ barL.getStreet();
                break;
            }

        }

        //img.setImageDrawable(Drawable);
        barTitle.setText(barName);
        barLoc.setText(barLocation);

        if (position%2 == 1)
            view.setBackgroundColor(Color.WHITE);
        else
            view.setBackgroundColor(Color.rgb(0xf6, 0xd6, 0xd6));//red-ish

        return view;
    }
}
