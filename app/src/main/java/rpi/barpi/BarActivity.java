package rpi.barpi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class BarActivity extends AppCompatActivity
{
    Bar bar=Data.bars.get(Data.mainAct.contextMenuItemSelected);

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        //set the title
        setTitle(bar.getName());
    }

    public void rateBar(View v)
    {
        int rating=0;//doesnt need to be a float, you cant do half stars or fractions

        if(v.getId() == R.id.ba_btnstarone) rating=1;
        else if(v.getId() == R.id.ba_btnstartwo) rating=2;
        else if(v.getId() == R.id.ba_btnstarthree) rating=3;
        else if(v.getId() == R.id.ba_btnstarfour) rating=4;
        else if(v.getId() == R.id.ba_btnstarfive) rating=5;

        if((rating < 1) || (rating > 5)) return;

        //tell the server
        /*ArrayList<String> data=new ArrayList<String>();
        data.add("2");
        data.add(Integer.toString(bar.getID()));
        data.add(Integer.toString(rating));

        Sockets.writeEngine(data);*/

        //debugging
        Toast.makeText(this, Integer.toString(rating)+" stars", Toast.LENGTH_SHORT).show();
    }
}
