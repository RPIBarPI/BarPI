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

import java.text.NumberFormat;
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


        boolean isSpecial=false;

        // Go thru all bars in list to lay them out
        for (int i=0; i<Data.barAct.bar.drinks.size(); ++i)
        {
            if(Data.barAct.bar.drinks.get(i).getID() == currentDrink.getID())
            {

                float price=Data.barAct.bar.drinks.get(i).getPrice();
                for(int j=0; j<Data.barAct.bar.specials.size(); ++j)
                {
                    //new price because of specials
                    if(Data.barAct.bar.specials.get(j).getDrinkID() == Data.barAct.bar.drinks.get(i).getID())
                    {
                        price=Data.barAct.bar.specials.get(j).getPrice();
                        isSpecial=true;
                    }
                }

                drinkTitle.setText(Data.barAct.bar.drinks.get(i).getName());
                drinkDesc.setText(Data.barAct.bar.drinks.get(i).getDescription().toString());
                NumberFormat format=NumberFormat.getCurrencyInstance();
                drinkPrice.setText(format.format(price));
                break;
            }
        }

        if(isSpecial)
        {
            view.setBackgroundColor(Color.RED);
        }
        else
        {
            if (position%2 == 1)
                view.setBackgroundColor(Color.WHITE);
            else
                view.setBackgroundColor(ResourcesCompat.getColor(view.getResources(), R.color.colorRedish, null));//red-ish
        }

        return view;
    }
}