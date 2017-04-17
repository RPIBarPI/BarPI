package rpi.barpi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DrinkAdapter extends ArrayAdapter<Drink> {
    private Context context;
    int resource;
    private ArrayList<Drink> drinks = new ArrayList<Drink>();

    public DrinkAdapter(Context context, int resource, ArrayList<Drink> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.drinks=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(resource, null);
        Drink currentDrink=drinks.get(position);

        //ImageView img = (ImageView)view.findViewById(R.id.drink_img);

        TextView drinkTitle = (TextView)view.findViewById(R.id.drink_title);
        TextView drinkDesc = (TextView)view.findViewById(R.id.drink_description);
        TextView drinkPrice = (TextView)view.findViewById(R.id.drink_price);


        // Go thru all bars in list to lay them out
        for (int i=0; i<Data.barAct.bar.drinks.size(); ++i) {
            if(Data.barAct.bar.drinks.get(i).getID() == currentDrink.getID()) {
                //img.setImageDrawable(Drawable);
                //barDesc.setText(Data.barAct.bar.getDescription());
                drinkTitle.setText(Data.barAct.bar.drinks.get(i).getName());
                drinkDesc.setText(Data.barAct.bar.drinks.get(i).getDescription().toString());
                drinkPrice.setText(Float.toString(Data.barAct.bar.drinks.get(i).getPrice()));
                break;
            }

        }

        if (position%2 == 1)
            view.setBackgroundColor(Color.WHITE);
        else
            view.setBackgroundColor(ResourcesCompat.getColor(view.getResources(), R.color.colorRedish, null));//red-ish

        return view;
    }
}