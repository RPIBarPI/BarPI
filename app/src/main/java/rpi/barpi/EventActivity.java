package rpi.barpi;

/**
 * Created by eryka (and rob, hi) on 3/26/2017.
 **/

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity
{
    Event event = Data.barAct.bar.events.get(Data.barAct.contextMenuItemSelected);

    RatingBar rb;
    TextView rbText;

    float eventRating=0.0f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Data.eventAct=this;

        // Set the title
        setTitle(Data.barAct.bar.getName());

        // Set other gui obj data
        rb=(RatingBar)findViewById(R.id.ratingBar);
        rbText=(TextView)findViewById(R.id.ratingBarText);
        TextView eventNm = (TextView)findViewById(R.id.eventName);
        eventNm.setText(event.getName());

        TextView eventDesc = (TextView)findViewById(R.id.eventDescript);
        eventDesc.setText(event.getDescription());

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
                    data.add(Integer.toString(Data.barAct.bar.getID()));
                    data.add(Integer.toString(event.getID()));//event id
                    data.add(Float.toString(rating-eventRating));//barRating is 0 if not rated

                    if((eventRating >= 1.0f) && (eventRating <= 5.0f))//bar already rated?
                        data.add("1");
                    else
                        data.add("0");

                    Sockets.writeEngine(data);

                    //save the rating you rated to the class & storage
                    eventRating=rating;
                    saveRating();

                    //some posivitve RFT and set the textview
                    rbText.setText("You rated this bar "+Float.toString(eventRating)+" stars!");
                    Toast.makeText(Data.barAct, "Thank you for rating!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //set the rating text
        if((eventRating >= 1.0f) && (eventRating <= 5.0f))
            rbText.setText("You rated this bar "+Float.toString(eventRating)+" stars!");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Data.eventAct=null;
    }

    private void loadRating()
    {
        eventRating=Data.saveData.getFloat("eventRating"+Integer.toString(event.getID()), 0.0f);
    }

    public void saveRating()
    {
        SharedPreferences.Editor SDEditor=Data.saveData.edit();
        SDEditor.putFloat("eventRating"+Integer.toString(event.getID()), eventRating);
        SDEditor.apply();
    }
}
