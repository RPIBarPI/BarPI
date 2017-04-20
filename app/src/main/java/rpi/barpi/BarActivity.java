package rpi.barpi;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class BarActivity extends AppCompatActivity implements OnMapReadyCallback
{
    public Bar bar=Data.bars.get(Data.mainAct.contextMenuItemSelected);
    private SupportMapFragment map=null;
    ListView lvEvents;
    EventAdapter eventAdapter=null;
    ListView lvDrinks;
    DrinkAdapter drinkAdapter=null;
    RatingBar rb;
    TextView rbText;

    public int contextMenuItemSelected = -1;
    float barRating=0.0f;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        Data.barAct=this;

        //set the title
        setTitle(bar.getName());

        //Set event gui objects
        map=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);
        rb=(RatingBar)findViewById(R.id.ratingBar);
        rbText=(TextView)findViewById(R.id.ratingBarText);
        lvEvents=(ListView)findViewById(R.id.eventListView);
        lvDrinks=(ListView)findViewById(R.id.drinkListView);

        //Set the lv arrays
        eventAdapter = new EventAdapter(this, R.layout.event_row_layout, bar.events);
        lvEvents.setAdapter(eventAdapter);
        drinkAdapter = new DrinkAdapter(this, R.layout.drink_row_layout, bar.drinks);
        lvDrinks.setAdapter(drinkAdapter);

        //other gui objs data
        TextView barDesc = (TextView)findViewById(R.id.barDescript);
        barDesc.setText(Data.barAct.bar.getDescription());
        rb.setRating(bar.getRating());

        loadRating();//load the rating from the saved data

        //Rating bar event "click"
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
            {
                if(fromUser)
                {
                    if((rating < 1.0f) || (rating > 5.0f)) return;

                    //tell the server
                    ArrayList<String> data=new ArrayList<String>();
                    data.add("1");
                    data.add(Integer.toString(bar.getID()));
                    data.add("0");//event id
                    data.add(Float.toString(rating-barRating));//barRating is 0 if not rated

                    if((barRating >= 1.0f) && (barRating <= 5.0f))//bar already rated?
                        data.add("1");
                    else
                        data.add("0");

                    Sockets.writeEngine(data);

                    //save the rating you rated to the class & storage
                    barRating=rating;
                    saveRating();

                    //some posivitve RFT and set the textview
                    rbText.setText("You rated this bar "+Float.toString(barRating)+" stars!");
                    Toast.makeText(Data.barAct, "Thank you for rating!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //set the rating text
        if((barRating >= 1.0f) && (barRating <= 5.0f))
            rbText.setText("You rated this bar "+Float.toString(barRating)+" stars!");

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

        //Get the bar's drinks, events and specials
        ArrayList<String> data=new ArrayList<String>();
        data.add("8");
        data.add(Integer.toString(bar.getID()));
        Sockets.writeEngine(data);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch(requestCode)
        {
            case 0:
            {
                if((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                {
                    //permission granted, continue
                }
                else//permission denied
                {
                    finish();
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map)
    {
        boolean permSet=false;
        //permissions
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        else
            permSet=true;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        else
            permSet=true;

        if(permSet)
        {
            if((bar.getLocation().getLongitude() != 360.0f) && (bar.getLocation().getLatitude() != 360.0f))
            {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(bar.getLocation().getLatitude(), bar.getLocation().getLongitude()), 16));
                map.setMyLocationEnabled(true);
            }
        }
    }

    private void loadRating()
    {
        barRating=Data.saveData.getFloat("barRating"+Integer.toString(bar.getID()), 0.0f);
    }

    public void saveRating()
    {
        SharedPreferences.Editor SDEditor=Data.saveData.edit();
        SDEditor.putFloat("barRating"+Integer.toString(bar.getID()), barRating);
        SDEditor.apply();
    }
}
