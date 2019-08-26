package com.ashinfo.stockrates.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashinfo.stockrates.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class StockListAdpter extends BaseAdapter {
    Context context;
    ArrayList<String> StockName, StockSymbol, StockType;

    public StockListAdpter (Context context1, ArrayList<String> stockName, ArrayList<String> stockSymbol, ArrayList<String> stockType){
        this.context = context1;
        this.StockName = stockName;
        this.StockSymbol = stockSymbol;
        this.StockType = stockType;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.single_grid_item_stocks, null);
            //holder = new RecyclerView.ViewHolder();
        } else {
            gridView = (View) convertView;
        }

        TextView StockNameText = (TextView)gridView.findViewById(R.id.stockNameText);
        TextView StockTypeText = (TextView)gridView.findViewById(R.id.stockTypeText);

        StockNameText.setText(StockName.get(position) + " (" + StockSymbol.get(position) + ") ");
        StockTypeText.setText(StockType.get(position));

        return gridView;
    }

    @Override
    public int getCount() {
        return StockName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
