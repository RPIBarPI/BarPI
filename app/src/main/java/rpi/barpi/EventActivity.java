package rpi.barpi;

/**
 * Created by eryka (and rob, hi) on 3/26/2017.
 **/
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class EventActivity extends AppCompatActivity {
    Event event = Data.barAct.bar.events.get(Data.barAct.contextMenuItemSelected);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Set the title
        setTitle(event.getName());
    }

    public void rateBar(View v)
    {
        float rating=0;

        /*if(v.getId() == R.id.ea_btnstarone) rating=1;
        else if(v.getId() == R.id.ea_btnstartwo) rating=2;
        else if(v.getId() == R.id.ea_btnstarthree) rating=3;
        else if(v.getId() == R.id.ea_btnstarfour) rating=4;
        else if(v.getId() == R.id.ea_btnstarfive) rating=5;

        if((rating < 1) || (rating > 5)) return;*/

        //tell the server
        /*ArrayList<String> data=new ArrayList<String>();
        data.add("3");
        data.add(Integer.toString(event.getID()));
        data.add(Integer.toString(rating));

        Sockets.writeEngine(data);*/

        //debugging
        Toast.makeText(this, Float.toString(rating)+" stars", Toast.LENGTH_SHORT).show();
    }
}
