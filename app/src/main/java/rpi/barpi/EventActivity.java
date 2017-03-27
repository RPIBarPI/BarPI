package rpi.barpi;

/**
 * Created by eryka on 3/26/2017.
 **/
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class EventActivity extends BarActivity {
    Event event = Bar.events.get(Bar.barAct.contextMenuItemSelected);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Set the title
        setTitle(event.getName());
    }
}
