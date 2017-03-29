package rpi.barpi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by matthewgilman on 3/27/17.
 */

public class SearchActivity extends AppCompatActivity {
    ArrayList<Bar> listItems=new ArrayList<Bar>(Data.bars);
    ArrayAdapter<Bar> adapter;
    ListView listView;
    EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);

        listView = (ListView) findViewById(R.id.search_list_view);

        editText = (EditText) findViewById(R.id.txtsearch);

        adapter = new ArrayAdapter<Bar>(this, R.layout.list_item, R.id.txtitem, listItems);
        listView.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
                if (s.toString().equals("")) {
                    // reset listview
                    listItems=new ArrayList<Bar>(Data.bars);
                    adapter.notifyDataSetChanged();
                } else {
                    // perform search
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    public void searchItem(String textToSearch)
    {
        Iterator<Bar> itr=listItems.iterator();
        textToSearch=textToSearch.toLowerCase();
        while(itr.hasNext())
        {
            Bar item=itr.next();
            if((!item.getName().toLowerCase().contains(textToSearch)) &&
                    (!item.getDescription().toLowerCase().contains(textToSearch)))
                itr.remove();
        }
        adapter.notifyDataSetChanged();
    }
}