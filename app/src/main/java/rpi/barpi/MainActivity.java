package rpi.barpi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.EditText;
import android.text.Editable;
import java.util.Arrays;
import android.text.TextWatcher;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ListView lvbars;
    ArrayAdapter<Bar> appAdapter = null;
    public int contextMenuItemSelected = -1;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Data.initialized)
            Data.init(getApplicationContext().getSharedPreferences("SaveData", Context.MODE_PRIVATE));

        Data.mainAct = this;

        //set the gui objects
        lvbars = (ListView) findViewById(R.id.barListView);

        //set the lvs array
        appAdapter = new ArrayAdapter<Bar>(this, android.R.layout.simple_list_item_1, Data.bars);
        lvbars.setAdapter(appAdapter);

        //long click event
        registerForContextMenu(lvbars);

        //click event
        final MainActivity tempThis = this;
        lvbars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                tempThis.contextMenuItemSelected = position;
                startActivity(new Intent(tempThis, BarActivity.class));
            }
        });

        //connect to the server


        //ArrayList<String> data = new ArrayList<String>();

        ArrayList<String> data=new ArrayList<String>();
        data.add(Data.VERSION);
        data.add("CONNECT");
        if(Data.regUserID > 0) data.add(Integer.toString(Data.regUserID));
        else data.add("0");

        Sockets.connect(data);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuinflater = getMenuInflater();
        //inflater.inflate(R.menu.bar_menu, menu);
        menuinflater.inflate(R.menu.bar_menu, menu);

        //incase we open a new activity from this one
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        contextMenuItemSelected = info.position;


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.bar_menu, menu);
        return super.onCreateOptionsMenu(menu);

        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuSearch) {
            //Toast.makeText(this, "Searching ...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SearchActivity.class));

        }
        if(id == R.id.bar_main_open){
            Toast.makeText(this,"opening ....",Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.bar_main_report){
            Toast.makeText(this,"reporting ....",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }





    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id == R.id.bar_main_open)
        {
            Toast.makeText(this, "Opening bar info...", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.bar_main_report)
        {
            Toast.makeText(this, "Reporting bar...", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(id == R.id.menuSearch)
        {
            Toast.makeText(this, "Searching ...", Toast.LENGTH_SHORT).show();
            // add search method here
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
