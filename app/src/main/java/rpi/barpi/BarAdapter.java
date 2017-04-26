package rpi.barpi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
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

        // Go thru all bars in list to lay them out
        for(int i=0;i<Data.bars.size();++i)
        {
            if(Data.bars.get(i).getID() == currentBar.getID())
            {
                setBarImage(img, "http://seanwaclawik.com/barpi/img/bars/"+Integer.toString(Data.bars.get(i).getID())+".jpg");
                barTitle.setText(Data.bars.get(i).getName());
                barLoc.setText(Data.bars.get(i).getLocation().toString());
                break;
            }
        }

        if (position%2 == 1)
            view.setBackgroundColor(Color.WHITE);
        else
            view.setBackgroundColor(ResourcesCompat.getColor(view.getResources(), R.color.colorRedish, null));//red-ish

        return view;
    }

    private void setBarImage(final ImageView img, final String url)
    {
        Runnable writeRun=new Runnable()
        {
            public void run()
            {
                try
                {
                    InputStream is=(InputStream)new URL(url).getContent();
                    Drawable d=Drawable.createFromStream(is, "bar");
                    img.setImageDrawable(d);
                }
                catch(Exception ex)
                {
                    //
                }
            }
        };
        Thread fluffyButtDawgThread=new Thread(writeRun);
        fluffyButtDawgThread.start();

    }
}
