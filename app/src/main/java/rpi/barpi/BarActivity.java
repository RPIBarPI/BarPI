package rpi.barpi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


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
}
