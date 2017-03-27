package rpi.barpi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by matthewgilman on 3/27/17.
 */

public class SearchActivity extends AppCompatActivity {
    //Bar bar = Data.bars.get(Data.mainAct.contextMenuItemSelected);
    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);

        listView = (ListView) findViewById(R.id.search_list_view);

        editText = (EditText) findViewById(R.id.txtsearch);

        initList();

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
                    initList();
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

    public void searchItem(String textToSearch) {
        for (String item : items) {
            if (!item.contains(textToSearch)) {
                listItems.remove(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void initList() {
        items = new String[]{"Bar Troy", "Ruck", "Olearys", "Union Pub"};
        listItems = new ArrayList<>(Arrays.asList(items));

        adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.txtitem, listItems);

        listView.setAdapter(adapter);

    }
}


