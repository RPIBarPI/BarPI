package rpi.barpi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity
{
    ListView lvbars;
    EditText searchEditText;

    ArrayList<Bar> barListItems=new ArrayList<Bar>(Data.bars);
    BarAdapter appAdapter=null;

    public int contextMenuItemSelected=-1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Data.initialized)
            Data.init(getApplicationContext().getSharedPreferences("SaveData", Context.MODE_PRIVATE));

        Data.mainAct = this;

        //set the gui objects. Inflates activity_main
        lvbars = (ListView) findViewById(R.id.barListView);
        searchEditText = (EditText) findViewById(R.id.txtsearch);

        //set the lvs array
        appAdapter = new BarAdapter(this, R.layout.bar_row_layout, barListItems);
        lvbars.setAdapter(appAdapter);

        //long click event
        registerForContextMenu(lvbars);

        //click event
        final MainActivity tempThis = this;
        lvbars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Bar tempBar=barListItems.get(position);
                for(int i=0;i<Data.bars.size();++i)
                {
                    if(Data.bars.get(i).getID() == tempBar.getID())
                    {
                        boolean permSet=false;
                        //permissions
                        if(ContextCompat.checkSelfPermission(tempThis, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(tempThis, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                        else
                            permSet=true;

                        if(ContextCompat.checkSelfPermission(tempThis, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(tempThis, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                        else
                            permSet=true;

                        if(permSet)
                        {
                            tempThis.contextMenuItemSelected=i;
                            startActivity(new Intent(tempThis, BarActivity.class));
                            break;
                        }
                    }
                }
            }
        });

        //for the search bar
        searchEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.toString().equals(""))
                {
                    //reset listview
                    initList();
                }
                else
                {
                    //perform search
                    initList();
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                //
            }
        });

        //refresh
        final SwipeRefreshLayout feedswiperefresh=(SwipeRefreshLayout)findViewById(R.id.feedswiperefresh);
        feedswiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener()
                {
                    @Override
                    public void onRefresh()
                    {
                        //request the bars again
                        ArrayList<String> data=new ArrayList<String>();
                        data.add("0");
                        data.add("password");

                        Sockets.writeEngine(data);

                        //stop the spinning thing
                        feedswiperefresh.setRefreshing(false);
                    }
                }
        );

        //connect to the server
        ArrayList<String> data=new ArrayList<String>();
        data.add(Data.VERSION);
        data.add("CONNECT");
        if(Data.regUserID >= 0) data.add(Integer.toString(Data.regUserID));
        else data.add("0");

        Sockets.connect(data);
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
                return;
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.bar_menu, menu);

        //incase we open a new activity from this one
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        contextMenuItemSelected = info.position;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id == R.id.bar_main_open)
        {
            Bar tempBar=barListItems.get(contextMenuItemSelected);
            for(int i=0;i<Data.bars.size();++i)
            {
                if(Data.bars.get(i).getID() == tempBar.getID())
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
                        contextMenuItemSelected=i;//reassign it
                        startActivity(new Intent(this, BarActivity.class));
                        break;
                    }
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void searchItem(String textToSearch)
    {
        Iterator<Bar> itr=barListItems.iterator();
        textToSearch=textToSearch.toLowerCase();
        while(itr.hasNext())
        {
            Bar item=itr.next();
            if((!item.getName().toLowerCase().contains(textToSearch)) &&
                    (!item.getDescription().toLowerCase().contains(textToSearch)))
                itr.remove();
        }
        appAdapter.notifyDataSetChanged();
    }

    public void initList()
    {
        barListItems.clear();
        barListItems.addAll(Data.bars);
        appAdapter.notifyDataSetChanged();
    }
}
