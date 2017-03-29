package rpi.barpi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class BarActivity extends AppCompatActivity
{
    public Bar bar=Data.bars.get(Data.mainAct.contextMenuItemSelected);
    ListView lvEvents;
    ArrayAdapter<Event> appAdapter=null;
    public int contextMenuItemSelected = -1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        Data.barAct=this;

        //set the title
        setTitle(bar.getName());

        //Set event gui objects
        lvEvents=(ListView)findViewById(R.id.eventListView);
        //Set the lv array
        appAdapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_1, bar.events);
        lvEvents.setAdapter(appAdapter);

        // Long click event
        registerForContextMenu(lvEvents);
        // Click event
        final BarActivity tempThis=this;
        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // Very similar to setup of MainActivity from commit w/ message "Added BarActivity and gradle update"
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                tempThis.contextMenuItemSelected = position;
                startActivity(new Intent(tempThis, EventActivity.class));
            }
        });

        //test data
        bar.events.clear();
        bar.events.add(new Event(0, "Ladies' Night", "$1 Well drinks for ladies"));
        bar.events.add(new Event(1, "$3 PBR Pitchers", "From 6-8pm"));
        bar.events.add(new Event(2, "Buy-one-get-one", "Mondays: 10-11pm"));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Data.barAct=null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.barchat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id == R.id.bar_chat)
        {
            startActivity(new Intent(this, BarChatActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
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
